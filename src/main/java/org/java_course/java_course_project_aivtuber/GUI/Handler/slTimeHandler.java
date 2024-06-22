package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import static org.java_course.java_course_project_aivtuber.Application.*;
import static org.java_course.java_course_project_aivtuber.GUI.Tool.TimeChanger.Seconds_toStr;

public class slTimeHandler implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent mouseEvent) {
        double mouseX = mouseEvent.getX();
        double sliderWidth = slTime.getWidth();

        double newValue = (mouseX / sliderWidth) * 100; // Calculate the new value as a percentage
        double newVideoTime = (newValue / 100) * endTime; // Convert the percentage to video time

        // Update the player to the new time
        players.get(currentPlayerIndex).seek(Duration.seconds(newVideoTime));

        // Update the slider's value and the label
        slTime.setValue(newValue);
        lbCurrentTime.setText(String.format("%s / %s", Seconds_toStr(newVideoTime), Seconds_toStr(endTime)));
    }
}
