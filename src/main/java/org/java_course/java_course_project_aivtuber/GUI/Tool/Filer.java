package org.java_course.java_course_project_aivtuber.GUI.Tool;

import javafx.stage.DirectoryChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Filer {

    public static File createFileSeq(String directory, String baseName, String extension) throws IOException {
        java.io.File directoryFile = new java.io.File(directory);
        if (! directoryFile.exists()) {
            directoryFile.mkdirs(); // 创建目录路径，如果不存在
        }
        String fileName = baseName + extension;
        java.io.File file = new java.io.File(directory + fileName);
        int count = 1;
        while (file.exists()) {
            fileName = baseName + "_" + count + extension;
            file = new java.io.File(directory + fileName);
            count++;
        }
        // 创建文件
        if (file.createNewFile()) {
            return file;
        } else {
            throw new IOException("无法创建文件，名字为: " + fileName);
        }
    }

    public static String[] toArray(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                // 去除行首尾的空白字符和双引号，移除末尾的逗号
                line = line.trim().replaceAll("^\"|\",$", "").trim();
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 将List转换为String数组
        return lines.toArray(new String[0]);
    }
}
