package org.java_course.java_course_project_aivtuber.Synchronous_callback;

import java.io.File;
import java.io.IOException;

import static org.java_course.java_course_project_aivtuber.GUI.Tool.WmvToMp3Converter.convertWmvToMp3;

public class Worker {

    private static final int CHECK_INTERVAL = 3000; // 检查间隔，以毫秒为单位

    public File executeWork(File file, Callback callback) throws IOException, InterruptedException {
        long lastFileSize = -1;
        long currentFileSize = file.length();
        File outputfile;
        while (true) {
            if (currentFileSize != lastFileSize) {
                lastFileSize = currentFileSize;
                try {
                    Thread.sleep(CHECK_INTERVAL);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.out.println("File is up to date");
                outputfile = convertWmvToMp3("src\\main\\resources\\voice_data\\", file);
                break;
            }
        }
        // 任务完成，调用回调方法
        callback.onComplete("Task Completed!");
        return outputfile;
    }
}
