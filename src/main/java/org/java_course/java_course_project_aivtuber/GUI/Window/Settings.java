package org.java_course.java_course_project_aivtuber.GUI.Window;

public class Settings {

    private String soundType;
    private String aiModelType;
    private String languageModule;
    private String Systemprompt;
    private String FFmpegPath;
    private String GPTSoVITSPath;
    private String Live2DViewerEXPath;
    private String Live2DModelPath;
    private String EnbleLive2DViewerEX;
    private String EnbleBlive;

    // Getters and Setters
    public String getEnbleBlive() {
        return EnbleBlive;
    }

    public void setEnbleBlive(String enbleBlive) {
        EnbleBlive = enbleBlive;
    }

    public String getEnbleLive2DViewerEX() {
        return EnbleLive2DViewerEX;
    }

    public void setEnbleLive2DViewerEX(String enbleLive2DViewerEX) {
        EnbleLive2DViewerEX = enbleLive2DViewerEX;
    }

    public void setLive2DModelPath(String live2DModelPath) {
        Live2DModelPath = live2DModelPath;
    }

    public String getLive2DModelPath() {
        return Live2DModelPath;
    }

    public void setLive2DViewerEXPath(String live2DViewerEXPath) {
        Live2DViewerEXPath = live2DViewerEXPath;
    }

    public void setGPTSoVITSPath(String GPTSoVITSPath) {
        this.GPTSoVITSPath = GPTSoVITSPath;
    }

    public String getLive2DViewerEXPath() {
        return Live2DViewerEXPath;
    }

    public String getGPTSoVITSPath() {
        return GPTSoVITSPath;
    }

    public String getSoundType() {
        return soundType;
    }

    public String getFFmpegPath() {
        return FFmpegPath;
    }

    public void setFFmpegPath(String FFmpegPath) {
        this.FFmpegPath = FFmpegPath;
    }

    public void setSoundType(String soundType) {
        this.soundType = soundType;
    }

    public String getAiModelType() {
        return aiModelType;
    }

    public void setAiModelType(String aiModelType) {
        this.aiModelType = aiModelType;
    }

    public String getLanguageModule() {
        return languageModule;
    }

    public void setLanguageModule(String languageModule) {
        this.languageModule = languageModule;
    }

    public String getSystemprompt() {
        return Systemprompt;
    }

    public void setSystemprompt(String Systemprompt) {
        this.Systemprompt = Systemprompt;
    }

}