package org.java_course.java_course_project_aivtuber.GUI.Tool;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;
import javazoom.jl.player.advanced.PlaybackEvent;
import javazoom.jl.player.advanced.PlaybackListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class DynamicAudioPlayer {

    private static final BlockingQueue<File> audioQueue = new LinkedBlockingQueue<>();
    private static volatile boolean stopPlaying = false;
    private static Thread playbackThread;
    private static final Object playbackLock = new Object(); // 锁对象用于同步播放

    public static void addToAudioQueue(File outputFile) {
        try {
            audioQueue.put(outputFile);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void processAudioQueue() {
        // 重置 stopPlaying 标志位
        stopPlaying = false;

        // 确保之前的线程已经终止
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
            try {
                playbackThread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        playbackThread = new Thread(() -> {
            while (!stopPlaying) {
                try {
                    File outputFile = audioQueue.take(); // Use take() to wait for elements
                    playAudio(outputFile);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        playbackThread.start();
    }

    private static void playAudio(File filePath) {
        synchronized (playbackLock) { // 使用同步块确保一个时间点只能播放一个音频文件
            try (FileInputStream fileInputStream = new FileInputStream(filePath.getAbsoluteFile())) {
                AdvancedPlayer player = new AdvancedPlayer(fileInputStream);
                player.setPlayBackListener(new PlaybackListener() {
                    @Override
                    public void playbackFinished(PlaybackEvent evt) {
                        // Notify when playback finishes
                        System.out.println("Playback finished for file: " + filePath.getName());
                        if (audioQueue.isEmpty()) {
                            stopPlaying = true; // Set flag to stop if queue is empty
                        }
                    }
                });
                player.play();
            } catch (JavaLayerException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopPlayback() {
        stopPlaying = true;
        if (playbackThread != null && playbackThread.isAlive()) {
            playbackThread.interrupt();
        }
    }

}
