package org.java_course.java_course_project_aivtuber.GUI.Window;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SettingsManager {
    private String SETTINGS_FILE;

    public SettingsManager(String SETTINGS_FILE) {
        this.SETTINGS_FILE = SETTINGS_FILE;
    }

    public void setName(String SETTINGS_FILE) {
        this.SETTINGS_FILE = SETTINGS_FILE;
    }

    public String getName() {
        return SETTINGS_FILE;
    }

    // 保存设置到文件
    public void saveSettings(Settings settings) {
        Properties properties = new Properties();
        properties.setProperty("soundType", settings.getSoundType());
        properties.setProperty("aiModelType", settings.getAiModelType());
        properties.setProperty("LanguageModule", settings.getLanguageModule());
        properties.setProperty("Systemprompt", settings.getSystemprompt());
        properties.setProperty("FFmpegPath", settings.getFFmpegPath());
        properties.setProperty("GPTSoVITSPath", settings.getGPTSoVITSPath());
        properties.setProperty("Live2DViewerEXPath", settings.getLive2DViewerEXPath());
        properties.setProperty("Live2DModelPath", settings.getLive2DModelPath());
        properties.setProperty("EnbleLive2DViewerEX", settings.getEnbleLive2DViewerEX());
        properties.setProperty("EnbleBlive", settings.getEnbleBlive());
        try (FileOutputStream fos = new FileOutputStream(SETTINGS_FILE)) {
            properties.store(fos, "Settings Configuration");
            System.out.println("Settings saved to file.");
        } catch (IOException e) {
            e.getCause();
        }
    }

    // 从配置文件加载设置
    public Settings loadSettings() {
        Settings settings = new Settings();
        Properties properties = new Properties();

        try (FileInputStream fis = new FileInputStream(SETTINGS_FILE)) {
            properties.load(fis);
            settings.setSoundType(properties.getProperty("soundType", "Hutao"));
            settings.setAiModelType(properties.getProperty("aiModelType", "mistral-7b-instruct"));
            settings.setLanguageModule(properties.getProperty("LanguageModule", "auto"));
            settings.setSystemprompt(properties.getProperty("Systemprompt", ""));
            settings.setFFmpegPath(properties.getProperty("FFmpegPath", ""));
            settings.setGPTSoVITSPath(properties.getProperty("GPTSoVITSPath", ""));
            settings.setLive2DViewerEXPath(properties.getProperty("Live2DViewerEXPath", ""));
            settings.setLive2DModelPath(properties.getProperty("Live2DModelPath", ""));
            settings.setEnbleLive2DViewerEX(properties.getProperty("EnbleLive2DViewerEX", "否"));
            settings.setEnbleBlive(properties.getProperty("EnbleBlive", "否"));
            System.out.println("Settings loaded from file.");
        } catch (IOException e) {
            System.out.println("Settings file not found, using default settings.");
        }

        return settings;
    }
}