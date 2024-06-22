package org.java_course.java_course_project_aivtuber.GUI.Handler;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import org.java_course.java_course_project_aivtuber.GUI.Runnable.PlayNextRunnable;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class btnRightJumpHandler implements EventHandler<MouseEvent>{
    private final PlayNextRunnable playNext;
    public btnRightJumpHandler(PlayNextRunnable playNext){
        this.playNext = playNext;
    }
    @Override
    public void handle(MouseEvent mouseEvent) {
        players.get(currentPlayerIndex).stop();
        if (currentPlayerIndex + 1 < players.size()) {  //如果在该索引上存在音乐
            currentPlayerIndex++;
            if (players.get(currentPlayerIndex) == null) {  //如果Mediaplayer 已被删除 创建一个新的 Mediaplayer
                Media media_temp = new Media(files.get(currentPlayerIndex).toString());
                MediaPlayer mediaPlayer_temp = new MediaPlayer(media_temp);
                players.set(currentPlayerIndex, mediaPlayer_temp);
            }
        } else {
            currentPlayerIndex = 0;
            if (players.get(currentPlayerIndex) == null) {  //如果Mediaplayer 已被删除 创建一个新的 Mediaplayer
                Media media_temp = new Media(files.get(currentPlayerIndex).toString());
                MediaPlayer mediaPlayer_temp = new MediaPlayer(media_temp);
                players.set(currentPlayerIndex, mediaPlayer_temp);
            }
        }

        btnPlay.setGraphic(imageView_play);
        playNext.run();//递归调用
    }
}
