package org.java_course.java_course_project_aivtuber.GUI.Tool;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class RandomTimerMessage {
    private static int messageIndex;
    private static String[] array;
    public static String randomMessage;
    public static boolean hasNewInput = false; // 标志变量，用于检测是否有新的输入
    public static void scheduleNext(Timer timer) {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (!hasNewInput) {
                    Random random = new Random();
                    array = Filer.toArray("E:\\code\\Java\\Java_course_project_AI-Vtuber\\src\\main\\resources\\text_data\\question.txt");
                    if (array.length > 0) { // 检查数组长度是否为正
                        messageIndex = random.nextInt(array.length); // 随机选择一个索引
                        randomMessage = array[messageIndex];
                    } else {
                        // 如果数组长度为0，可以在这里处理，比如记录日志或设置默认消息
                        System.out.println("Array is empty or file reading failed.");
                    }
                }
                // 重置标志变量
                hasNewInput = false;
                scheduleNext(timer); // 重新调度下一次任务
            }
        };

        Random random = new Random();
        int delay = 10000 + random.nextInt(29000); // 生成10秒到30秒之间的随机延迟
        timer.schedule(task, delay);
    }
}
