package org.java_course.java_course_project_aivtuber.OpenRouterAPI;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyThread_APIConnection extends Thread {

    @Override
    public void run() {
        //反射APIConnection类, 调用该类的main
        Class<?> APIConnectionClass;
        try {
            APIConnectionClass = Class.forName("org.java_course.java_course_project_aivtuber.OpenRouterAPI.OpenRouterAPI");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Method APIConnectionMethod;
        try {
            APIConnectionMethod = APIConnectionClass.getMethod("main", String[].class);
            APIConnectionMethod.setAccessible(true);
            String[] mainArgs = {""};
            APIConnectionMethod.invoke(null, (Object) mainArgs);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }

    }

}
