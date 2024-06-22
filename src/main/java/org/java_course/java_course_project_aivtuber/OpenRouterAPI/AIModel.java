package org.java_course.java_course_project_aivtuber.OpenRouterAPI;

import java.util.List;

public class AIModel {

    private String id;
    private String model;
    private String object;
    private long created;
    private List<Choices> choices;
    private Usage usage;

    public AIModel(){}
    public AIModel(String id, String model, String object, long created, List<Choices> choices, Usage usage){
        this.id = id;
        this.model = model;
        this.object = object;
        this.created = created;
        this.choices = choices;
        this.usage = usage;
    }

    public String getId() {
        return id;
    }

    public String getModel() {
        return model;
    }

    public String getObject() {
        return object;
    }

    public long getCreated() {
        return created;
    }

    public List<Choices> getChoices() {
        return choices;
    }

    public Usage getUsage() {
        return usage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public void setChoices(List<Choices> choices) {
        this.choices = choices;
    }

    public void setUsage(Usage usage) {
        this.usage = usage;
    }

}

