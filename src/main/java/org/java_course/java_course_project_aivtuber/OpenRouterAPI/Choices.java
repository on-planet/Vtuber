package org.java_course.java_course_project_aivtuber.OpenRouterAPI;

public class Choices {

    private int index;
    private Message message;
    private String finish_reason;

    public Choices(){}
    public Choices(int index, Message message, String finish_reason){
        this.index = index;
        this.message = message;
        this.finish_reason = finish_reason;
    }

    public int getIndex() {
        return index;
    }

    public Message getMessage() {
        return message;
    }

    public String getFinish_reason() {
        return finish_reason;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setFinish_reason(String finish_reason) {
        this.finish_reason = finish_reason;
    }

    public String toString(){
        return message.toString();
    }

}
