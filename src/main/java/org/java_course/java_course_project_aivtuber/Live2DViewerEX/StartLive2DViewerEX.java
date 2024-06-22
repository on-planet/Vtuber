package org.java_course.java_course_project_aivtuber.Live2DViewerEX;

import org.java_course.java_course_project_aivtuber.GUI.Tool.Port;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;

import java.io.IOException;

import static org.java_course.java_course_project_aivtuber.Application.textArea_output;
import static org.java_course.java_course_project_aivtuber.Live2DViewerEX.MyThread_StatrLive2DViewerEX.Live2DViewerEX;

public class StartLive2DViewerEX {

    public static Process Live2DViewerEXProcess;
    private static final long checkIntervalMillis = 5000;
    public static void main(String[] mainArgs) {
        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(currentSettings.getLive2DViewerEXPath());
            Live2DViewerEXProcess = processBuilder.start();
            Port.waitForPort( 10086, checkIntervalMillis);
            Live2DViewerEX = true;
            textArea_output.appendText(">> Live2DViewerEX启动成功"+ "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
