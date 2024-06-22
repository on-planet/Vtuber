package org.java_course.java_course_project_aivtuber.GUI.Tool;

import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;

import java.io.File;
import java.io.IOException;

import static org.java_course.java_course_project_aivtuber.GUI.Tool.Filer.createFileSeq;

public class WmvToMp3Converter {

    public static File convertWmvToMp3(String inputPath, File file) throws IOException, InterruptedException {
        File outputFile = createFileSeq(inputPath, "voice", ".mp3");
        System.out.println(file.getAbsolutePath());
        System.out.println(outputFile.getAbsolutePath());
        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();
        // 构建FFmpeg命令
        String[] command = {
                currentSettings.getFFmpegPath(),
                "-i", file.getAbsolutePath(),
                "-vn",
                "-ar", "44100",
                "-ac", "2",
                "-b:a", "192k","-y",
                outputFile.getAbsolutePath()
        };

        // 执行FFmpeg命令
        ProcessBuilder processBuilder = new ProcessBuilder(command);
        processBuilder.inheritIO();
        Process process = processBuilder.start();
        process.waitFor();
        return outputFile;
    }

}
