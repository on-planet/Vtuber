package org.java_course.java_course_project_aivtuber.Live2DViewerEX;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyThread_StatrLive2DViewerEX extends Thread {
    public static volatile boolean Live2DViewerEX = false;
    @Override
    public void run() {
        //反射StartLive2DViewerEX类, 调用该类的main
        Class<?> StartLive2DViewerEXClass;
        try {
            StartLive2DViewerEXClass = Class.forName("org.java_course.java_course_project_aivtuber.Live2DViewerEX.StartLive2DViewerEX");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Method StartLive2DViewerEXMethod;
        try {
            StartLive2DViewerEXMethod = StartLive2DViewerEXClass.getMethod("main", String[].class);
            StartLive2DViewerEXMethod.setAccessible(true);
            String[] mainArgs = {""};
            StartLive2DViewerEXMethod.invoke(null, (Object) mainArgs);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
