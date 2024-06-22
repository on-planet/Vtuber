package org.java_course.java_course_project_aivtuber.GUI.Runnable;

import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class OpenMusicFolderRunnable implements Runnable {
    private final Stage stage;

    public OpenMusicFolderRunnable(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        DirectoryChooser directoryChooser = new DirectoryChooser();  // 打开文件夹
        directoryChooser.setTitle("打开文件夹");
        File selectedDirectory = directoryChooser.showDialog(stage);  // 保存至selectedDirectory变量里
        if (selectedDirectory != null) {
            File[] musicfiles = new File(selectedDirectory.toString()).listFiles(pathname -> {   // 创建File类的数组并读取selectedDirectory里的文件
                for (String ext : SUPPORTED_FILE_EXTENSIONS) {
                    if (pathname.getName().endsWith(ext)) {  // 文件过滤器筛选后缀文件
                        return true;
                    }
                }
                return false;
            });
            if (musicfiles != null) {
                for (File file : musicfiles) {
                    System.out.println(file);
                    files.add(file);
                    fileList.add(new File(file.getName()));
                    Media media = new Media(file.toURI().toString());  // 将file变量里的文件转换为String类型
                    MediaPlayer mediaPlayer = new MediaPlayer(media);   // 创建新的mediaPlayer
                    players.add(mediaPlayer);
                    listView.setItems(fileList);   // 右侧框架里的listView显示控件添加元素
                }
            }
            if (!flag) {   // 判定第一个预加载文件是否已经被删除
                players.removeFirst();
                flag = true;
            }
        }
    }
}
