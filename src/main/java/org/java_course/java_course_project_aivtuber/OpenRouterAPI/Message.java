package org.java_course.java_course_project_aivtuber.OpenRouterAPI;

public class Message {

    private String role;
    private String content;

    public Message(){}
    public Message(String id, String message){
        this.role = id;
        this.content = message;
    }

    public String getRole() {
        return role;
    }

    public String getContent() {
        return content;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String toString(){
        return role + " " + content;
    }

}
