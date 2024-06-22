package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference.MyThread_GPTSoVITS_Inference;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;
import org.java_course.java_course_project_aivtuber.Live2DViewerEX.MyThread_StatrLive2DViewerEX;
import org.java_course.java_course_project_aivtuber.Live2DViewerEX.MyThread_WebSocket;

import java.util.Objects;
import java.util.concurrent.CompletableFuture;

import static org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference.MyThread_GPTSoVITS_Inference.GPTSoVITSTask;
import static org.java_course.java_course_project_aivtuber.Live2DViewerEX.MyThread_StatrLive2DViewerEX.Live2DViewerEX;

public class LeftStarterHandler implements EventHandler<MouseEvent> {
    public static MyThread_WebSocket myThread_WebSocket = new MyThread_WebSocket();

    @Override
    public void handle(MouseEvent event) {
        Task<Void> task = new Task<>() {  //创建新线程
            @Override
            protected Void call() {
                SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
                Settings currentSettings = settingsManager.loadSettings();
                CompletableFuture.runAsync(() -> {
                    if(!Live2DViewerEX && Objects.equals(currentSettings.getEnbleLive2DViewerEX(), "是")){
                        MyThread_StatrLive2DViewerEX myThreadStatrLive2DViewerEX = new MyThread_StatrLive2DViewerEX();
                        myThreadStatrLive2DViewerEX.start();
                        myThreadStatrLive2DViewerEX.setDaemon(true);
                    }
                });
                CompletableFuture.runAsync(() -> {
                    if (!GPTSoVITSTask) {
                        MyThread_GPTSoVITS_Inference myThread_gptSoVITS_inference = new MyThread_GPTSoVITS_Inference();
                        //启动gptSoVITS_inference
                        myThread_gptSoVITS_inference.start();
                        myThread_gptSoVITS_inference.setDaemon(true);
                    }
                });
                return null;
            }
        };
        new Thread(task).start();
    }
}