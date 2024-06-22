package org.java_course.java_course_project_aivtuber.GUI.Listener;

import org.java_course.java_course_project_aivtuber.GUI.Runnable.PlayNextRunnable;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class EndOfMediaListener implements Runnable {
    private final PlayNextRunnable playNext;
    public EndOfMediaListener(PlayNextRunnable playNext) {
        this.playNext = playNext;
    }
    @Override
    public void run() {
        if (currentPlayerIndex + 1 < players.size()) {  //如果当前下一个索引没超过上限，就递增
            currentPlayerIndex++;
        } else {   //否则归零
            currentPlayerIndex = 0;
        }
        btnPlay.setGraphic(imageView_play);
        if (currentPlayerIndex < players.size()) {  //如果索引小于音乐序列的大小，便播放下一首音乐
            playNext.run();  //递归调用
        }
    }
}
