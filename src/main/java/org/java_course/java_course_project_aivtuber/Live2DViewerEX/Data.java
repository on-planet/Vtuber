package org.java_course.java_course_project_aivtuber.Live2DViewerEX;

import java.util.List;

public class Data {
    private int id;
    private String text;
    private List<String> choices;
    private int textFrameColor;
    private int textColor;
    private int duration;
    private String file;
    private int type;
    private int expId;
    private String hitArea;

    public Data(int id, String text, int textFrameColor, int textColor, int duration, String file, int type,
                List<String> choices, int expId) {
        this.id = id;
        this.text = text;
        this.textFrameColor = textFrameColor;
        this.textColor = textColor;
        this.duration = duration;
        this.file = file;
        this.type = type;
        this.choices = choices;
        this.expId = expId;
    }

    public Data(int id, String text, int textFrameColor, int textColor, int duration, String file, int type) {
        this.id = id;
        this.text = text;
        this.textFrameColor = textFrameColor;
        this.textColor = textColor;
        this.duration = duration;
        this.file = file;
        this.type = type;
    }

    public Data(int id, String text, int textFrameColor, int textColor, int duration) {
        this.id = id;
        this.text = text;
        this.textFrameColor = textFrameColor;
        this.textColor = textColor;
        this.duration = duration;

    }

    public Data(int id, String text, int textFrameColor, int textColor) {
        this.id = id;
        this.text = text;
        this.textFrameColor = textFrameColor;
        this.textColor = textColor;
    }

    public Data(int id, String text, int textFrameColor) {
        this.id = id;
        this.text = text;
        this.textFrameColor = textFrameColor;
    }

    public Data(int id, String file) {
        this.id = id;
        this.file = file;
    }

    public Data(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getExpId() {
        return expId;
    }

    public void setExpId(int expId) {
        this.expId = expId;
    }

    public String getText() {
        return text;
    }

    public List<String> getChoices() {
        return choices;
    }

    public int getTextFrameColor() {
        return textFrameColor;
    }

    public int getTextColor() {
        return textColor;
    }

    public int getDuration() {
        return duration;
    }

    public String getFile() {
        return file;
    }

    public int getType() {
        return type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public void setTextFrameColor(int textFrameColor) {
        this.textFrameColor = textFrameColor;
    }

    public void setTextColor(int textColor) {

        this.textColor = textColor;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public void setType(int type) {
        this.type = type;
    }
}
