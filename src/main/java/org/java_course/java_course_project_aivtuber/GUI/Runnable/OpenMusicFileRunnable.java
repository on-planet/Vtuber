package org.java_course.java_course_project_aivtuber.GUI.Runnable;

import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class OpenMusicFileRunnable implements Runnable {

    private final Stage stage;

    public OpenMusicFileRunnable(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void run() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("MP3 Music", "*.mp3"),
                new FileChooser.ExtensionFilter("All Music Files", "*.*")
        );
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            Platform.runLater(() -> {
                System.out.println(file.getName());
                files.add(file);
                fileList.add(new File(file.getName()));
                // 更新 UI
                listView.setItems( fileList);
            });

            Media media = new Media(file.toURI().toString());
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            // 加载完成后添加到播放器列表
            mediaPlayer.setOnReady(() -> {
                if (!flag && !players.isEmpty()) {
                    Platform.runLater(players::removeFirst);
                    flag = true;
                }
                players.add(mediaPlayer);
            });
        }
    }
}
