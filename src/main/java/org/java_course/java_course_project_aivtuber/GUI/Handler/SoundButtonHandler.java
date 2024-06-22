package org.java_course.java_course_project_aivtuber.GUI.Handler;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class SoundButtonHandler implements EventHandler<ActionEvent> {

    @Override
    public void handle(ActionEvent actionEvent) {
        double current_VideoSound = slVolume.getValue();
        if (btn_sound.getGraphic() == imageView_btn_sound) {
            btn_sound.setGraphic(imageView_btn_nosound);
            slVolume.setValue(0);
        } else {
            btn_sound.setGraphic(imageView_btn_sound);
            slVolume.setValue(current_VideoSound);
            slVolume.setValue(50);
        }
    }
}
