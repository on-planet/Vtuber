package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.java_course.java_course_project_aivtuber.GUI.Tool.AutomaticSummarization;

import java.io.IOException;

import static org.java_course.java_course_project_aivtuber.Application.textArea_output;

public class TopicAnalysis implements EventHandler<ActionEvent> {
    @Override
    public void handle(ActionEvent actionEvent) {
        try {
            AutomaticSummarization.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
