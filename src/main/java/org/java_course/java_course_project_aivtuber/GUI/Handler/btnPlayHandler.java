package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class btnPlayHandler implements EventHandler<ActionEvent>{
    @Override
    public void handle(ActionEvent actionEvent) {
        if (btnPlay.getGraphic() == imageView_play) {  //播放键为PLAY时
            if(currentPlayerIndex + 1 == players.size()){  //判断如果下一个Mediaplayer到达边界，就回到最初的Mediaplayer
            }else {
                btnPlay.setGraphic(imageView_stop);
                players.get(currentPlayerIndex).play();
            }
        } else {  //播放键为STOP时
            btnPlay.setGraphic(imageView_play);
            players.get(currentPlayerIndex).pause();
        }
    }
}
