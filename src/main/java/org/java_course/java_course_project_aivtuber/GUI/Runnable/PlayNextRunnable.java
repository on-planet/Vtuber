package org.java_course.java_course_project_aivtuber.GUI.Runnable;

import javafx.beans.binding.Bindings;
import org.java_course.java_course_project_aivtuber.GUI.Handler.*;
import org.java_course.java_course_project_aivtuber.GUI.Listener.EndOfMediaListener;
import org.java_course.java_course_project_aivtuber.GUI.Listener.MetaDataListener;
import org.java_course.java_course_project_aivtuber.GUI.Listener.slTimeStylePropertyListener;
import org.java_course.java_course_project_aivtuber.GUI.Listener.slTimeTimePropertyListener;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class PlayNextRunnable implements Runnable {

    @Override
    public void run() {

        players.get(currentPlayerIndex).play();
        btnPlay.setGraphic(imageView_stop);

        //按钮监听事件
        btnPlay.setOnAction(new btnPlayHandler());

        //右跳过键事件绑定
        btn_rightJump.setOnMouseClicked(new btnRightJumpHandler(this));

        //左跳过键事件绑定
        btn_leftJump.setOnMouseClicked(new btnLeftJumpHandler(this));

        //音频播放事件监听
        players.get(currentPlayerIndex).setOnPlaying(new MetaDataListener());   //播放开始

        //音频结束事件监听
        players.get(currentPlayerIndex).setOnEndOfMedia(new EndOfMediaListener(this));

        //对目前时间线进行 监听
        players.get(currentPlayerIndex).currentTimeProperty().addListener(new slTimeTimePropertyListener());

        slTime.setOnMouseClicked(new slTimeHandler());

        //时间线样式绑定
        slTime.styleProperty().bind(Bindings.createStringBinding(new slTimeStylePropertyListener(), slTime.valueProperty(), slTime.minProperty(), slTime.maxProperty()));

        //时间线 值 监听
        slTime.valueProperty().addListener(_ -> {
            if (slTime.isValueChanging()) {
                players.get(currentPlayerIndex).seek(players.get(currentPlayerIndex).getTotalDuration().multiply(slTime.getValue() / 100));
            }
        });

        //绑定目前Mediaplyer的音量属性
        players.get(currentPlayerIndex).volumeProperty().bind(slVolume.valueProperty().divide(100)); // 音量 绑定 + 调节
    }
}
