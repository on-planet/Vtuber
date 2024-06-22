package org.java_course.java_course_project_aivtuber.GUI.Tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Port {

        public static boolean isPortInUse(int port) {
            String command = "netstat -aon | findstr " + port;
            try {
                // 执行命令
                Process process = Runtime.getRuntime().exec(new String[] {"cmd.exe", "/c", command});

                // 获取命令输出
                BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                String line;
                while ((line = reader.readLine()) != null) {
                    // 输出命令结果行
                    System.out.println(line);
                    if (line.contains("LISTENING")) {
                        return true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }

        public static void waitForPort(int port, long checkIntervalMillis) {
            System.out.println("Waiting for port " + port + " to enter LISTENING state...");
            while (!isPortInUse(port)) {
                try {
                    Thread.sleep(checkIntervalMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Port " + port + " is now in LISTENING state.");
        }

        public static String findPID(int port) throws Exception {
            String command = "";
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                command = "netstat -ano | findstr :" + port;
            } else {
                command = "lsof -i :" + port;
            }

            Process process = Runtime.getRuntime().exec(command);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                if (os.contains("win")) {
                    String[] tokens = line.trim().split("\\s+");
                    return tokens[tokens.length - 1];  // Return the PID
                } else {
                    if (line.startsWith("COMMAND")) {
                        continue;
                    }
                    String[] tokens = line.trim().split("\\s+");
                    return tokens[1];  // Return the PID
                }
            }
            return null;
        }

        public static void killProcess(String pid) throws Exception {
            String command = "";
            String os = System.getProperty("os.name").toLowerCase();
            if (os.contains("win")) {
                command = "taskkill /PID " + pid + " /F";
            } else {
                command = "kill -9 " + pid;
            }

            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            System.out.println("Process with PID " + pid + " has been terminated.");
        }
}
