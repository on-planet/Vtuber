package org.java_course.java_course_project_aivtuber.GUI.Listener;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import static org.java_course.java_course_project_aivtuber.Application.*;
import static org.java_course.java_course_project_aivtuber.GUI.Tool.TimeChanger.Seconds_toStr;

public class slTimeTimePropertyListener implements InvalidationListener {

    @Override
    public void invalidated(Observable observable) {
        current_VideoTime = players.get(currentPlayerIndex).getCurrentTime().toSeconds();    //获取 现在时间
        lbCurrentTime.setText(STR."\{Seconds_toStr(current_VideoTime)} / \{Seconds_toStr(endTime)}");  //将 现在时间 和 结束时间 添加至 label 中
        slTime.setValue(current_VideoTime / endTime * 100);
    }
}
