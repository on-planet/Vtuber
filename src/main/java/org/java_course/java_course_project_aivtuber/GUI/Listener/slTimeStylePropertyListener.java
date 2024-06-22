package org.java_course.java_course_project_aivtuber.GUI.Listener;

import java.util.concurrent.Callable;

import static org.java_course.java_course_project_aivtuber.Application.slTime;

public class slTimeStylePropertyListener implements Callable<String> {
    @Override
    public String call() {
        double percentage = (slTime.getValue() - slTime.getMin()) / (slTime.getMax() - slTime.getMin()) * 100.0;
        return String.format("-slider-track-color: linear-gradient(to right, -slider-filled-track-color 0%%, "
                        + "-slider-filled-track-color %f%%, -fx-base %f%%, -fx-base 100%%);",
                percentage, percentage);
    }
}
