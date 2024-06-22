package org.java_course.java_course_project_aivtuber.GUI.Tool;

public class TimeChanger {
    public static String Seconds_toStr(Double seconds) {
        int count = seconds.intValue();
        int Hours = count / 3600;
        count = count % 3600;
        int Minutes = count / 60;
        count = count % 60;
        return String.format("%02d" + ":" + "%02d" + ":" + "%02d", Hours, Minutes, count);  //格式化
    }
}
