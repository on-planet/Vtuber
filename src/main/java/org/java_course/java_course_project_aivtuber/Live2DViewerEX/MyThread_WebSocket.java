package org.java_course.java_course_project_aivtuber.Live2DViewerEX;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyThread_WebSocket extends Thread {
    @Override
    public void run() {
        //反射WebSocket类, 调用该类的main

        Class<?> WebSocketClientClass;
        try {
            WebSocketClientClass = Class.forName("org.java_course.java_course_project_aivtuber.Live2DViewerEX.API" +
                    ".WebSocketClient");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Method WebSocketMethod;
        try {
            WebSocketMethod = WebSocketClientClass.getMethod("main", String[].class);
            WebSocketMethod.setAccessible(true);
            String[] mainArgs = {""};
            WebSocketMethod.invoke(null, (Object) mainArgs);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
