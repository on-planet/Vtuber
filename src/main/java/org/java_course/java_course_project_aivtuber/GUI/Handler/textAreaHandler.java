package org.java_course.java_course_project_aivtuber.GUI.Handler;

import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;

import java.io.IOException;
import java.nio.file.*;

import java.util.*;

import static org.java_course.java_course_project_aivtuber.Application.textArea_BliveMessage;

public class textAreaHandler{

    public static class FilePollingWatcher {
        private final Path filePath;
        private final List<String> processedSegments;

        public FilePollingWatcher(String filePath) {
            this.filePath = Paths.get(filePath);
            this.processedSegments = new ArrayList<>();
        }

        public void startWatching() throws IOException {
            Timer timer = new Timer(true);
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    try {
                        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
                        Settings currentSettings = settingsManager.loadSettings();
                        String content = Files.readString(filePath);
                        // 提取所有段落
                        List<String> segments = getAllSegments(content);
                        for (String segment : segments) {
                            if (!processedSegments.contains(segment) && currentSettings.getEnbleBlive().equals("是")) {
                                processedSegments.add(segment);
                                onFileContentChanged(segment);
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 1000); // 每秒检查一次
        }

        private void onFileContentChanged(String content) {
            // 处理文件内容变化
            System.out.println("File content changed:");
            System.out.println(content);
            textArea_BliveMessage.appendText(">> " + content + "\n");
        }

        private List<String> getAllSegments(String content) {
            // 假设段落是用换行符分隔
            return Arrays.asList(content.split("\\r?\\n"));
        }
    }
}


