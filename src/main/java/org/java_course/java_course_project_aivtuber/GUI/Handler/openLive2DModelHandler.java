package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.java_course.java_course_project_aivtuber.GUI.Window.Settings;
import org.java_course.java_course_project_aivtuber.GUI.Window.SettingsManager;

import java.io.File;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class openLive2DModelHandler implements EventHandler<ActionEvent> {
    private final Stage stage;
    public openLive2DModelHandler(Stage stage) {
        this.stage = stage;
    }
    @Override
    public void handle(ActionEvent event) {
        SettingsManager settingsManager = new SettingsManager("modelConfig.properties");
        Settings currentSettings = settingsManager.loadSettings();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择 Live2D 模型");
        fileChooser.showOpenDialog(stage);
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            currentSettings.setLive2DModelPath(selectedFile.getAbsolutePath());
            textArea_output.appendText(">> 模型添加成功" + "\n");
        }
    }
}

