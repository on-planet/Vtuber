package org.java_course.java_course_project_aivtuber.GPT_soVITS_Inference;

import org.java_course.java_course_project_aivtuber.GUI.Tool.Port;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static org.java_course.java_course_project_aivtuber.Application.textArea_output;

public class MyThread_GPTSoVITS_Inference extends Thread {
    public static volatile boolean GPTSoVITSTask = false;
    public static  Process GPTSoVITSProcess;
    private static final long checkIntervalMillis = 5000;

    @Override
    public void run() {
        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();
        ProcessBuilder processBuilder = new ProcessBuilder("cmd.exe", "/k", currentSettings.getGPTSoVITSPath());
        processBuilder.redirectErrorStream(true); // 合并错误流到标准输出流
        try {
            // 启动进程
            GPTSoVITSProcess = processBuilder.start();
            // 获取进程的输入流（输出结果）
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(GPTSoVITSProcess.getInputStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println(line);
                    Port.waitForPort( 5000, checkIntervalMillis);
                    GPTSoVITSTask = true;
                }
            }
            int exitcode = GPTSoVITSProcess.waitFor();
            System.exit(exitcode);
        } catch (IOException e) {
            e.getCause();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
