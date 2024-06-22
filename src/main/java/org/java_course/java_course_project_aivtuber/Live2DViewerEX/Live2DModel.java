package org.java_course.java_course_project_aivtuber.Live2DViewerEX;

public class Live2DModel {
    private int msg;
    private int msgId;
    private Data data;

    Live2DModel (int msg, int msgId, Data data) {
        this.msg = msg;
        this.msgId = msgId;
        this.data = data;
    }

    public Live2DModel(int msg, int msgId) {
        this.msg = msg;
        this.msgId = msgId;
        this.data = null;
    }

    public int getMsg() {
        return msg;
    }
    public int getMsgId() {
        return msgId;
    }
    public Data getData() {
        return data;
    }
    public void setMsg(int msg) {
        this.msg = msg;
    }
    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }
    public void setData(Data data) {
        this.data = data;
    }
}

