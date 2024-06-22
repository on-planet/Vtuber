package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import org.java_course.java_course_project_aivtuber.GUI.Tool.Filer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static org.java_course.java_course_project_aivtuber.Application.textArea_output;

public class saveDialogueHandler implements EventHandler<ActionEvent> {
    private final Stage stage;
    public saveDialogueHandler(Stage stage){
        this.stage = stage;
    }
    @Override
    public void handle(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("选择保存路径");
        String selectedDirectory = String.valueOf(directoryChooser.showDialog(stage));
        File dialogue;
        if (selectedDirectory != null) {
            try {
                dialogue = Filer.createFileSeq(selectedDirectory, "dialogue", ".txt");
                try(FileWriter writer = new FileWriter(dialogue.getAbsolutePath())){
                    writer.write(textArea_output.getText());
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
