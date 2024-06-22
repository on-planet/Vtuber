package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.beans.binding.Bindings;

import static org.java_course.java_course_project_aivtuber.Application.slVolume;

public class VolumeSliderStyler {

    public static void applyStyle() {
        slVolume.styleProperty().bind(
                Bindings.createStringBinding(() -> {
                    double percentage = (slVolume.getValue() - slVolume.getMin()) / (slVolume.getMax() - slVolume.getMin()) * 100.0;
                    return String.format(
                            "-slider-track-color: linear-gradient(to right, -slider-filled-track-color 0%%, "
                                    + "-slider-filled-track-color %f%%, -fx-base %f%%, -fx-base 100%%);",
                            percentage, percentage
                    );
                }, slVolume.valueProperty(), slVolume.minProperty(), slVolume.maxProperty())
        );
    }
}
