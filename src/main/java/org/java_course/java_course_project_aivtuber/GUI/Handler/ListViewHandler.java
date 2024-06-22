package org.java_course.java_course_project_aivtuber.GUI.Handler;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class ListViewHandler implements EventHandler<MouseEvent> {
    private final Runnable playNext;
    private final Runnable openMusicFile;
    private final Runnable openMusicFolder;

    public ListViewHandler(Runnable playNext, Runnable openMusicFile, Runnable openMusicFolder) {
        this.playNext = playNext;
        this.openMusicFile = openMusicFile;
        this.openMusicFolder = openMusicFolder;
    }

    @Override
    public void handle(MouseEvent mouseEvent) {
        // 限定为鼠标双击
        if (mouseEvent.getClickCount() == 2 && !listView.getItems().isEmpty()) {
            // 当前播放音乐停止
            System.out.println("players size: " + players.size());
            System.out.println("listView.getItems(): " + listView.getItems().size() + " items");
            players.get(currentPlayerIndex).stop();
            // 更新 currentPlayerIndex
            int selectMusicIndex = listView.getSelectionModel().getSelectedIndex();
            currentPlayerIndex = selectMusicIndex;
            System.out.println(selectMusicIndex);
            // 创建临时 Media 和临时 MediaPlayer
            Media media_temp = new Media(files.get(currentPlayerIndex).toURI().toString());
            MediaPlayer mediaPlayer_temp = new MediaPlayer(media_temp);
            if (currentPlayerIndex < players.size()) {
                players.set(currentPlayerIndex, mediaPlayer_temp);
            } else {
                players.removeAll();
                players.set(currentPlayerIndex, mediaPlayer_temp);
            }
            btnPlay.setGraphic(imageView_play);
            playNext.run();
        }

        // 限定为鼠标右键
        if (mouseEvent.getButton() == MouseButton.SECONDARY) {
            // 显示菜单
            contextMenu.show(listView, mouseEvent.getScreenX(), mouseEvent.getScreenY());
            int selectMusicIndex = listView.getSelectionModel().getSelectedIndex();
            // 移除音乐文件 动作监听
            contextMenu.getItems().get(0).setOnAction(_ -> {
                if (selectMusicIndex == currentPlayerIndex) {
                    players.get(currentPlayerIndex).stop();
                    btnPlay.setGraphic(imageView_play);
                }
                players.remove(selectMusicIndex);
                fileList.remove(selectMusicIndex);
                files.remove(selectMusicIndex);
                listView.setItems(fileList);
            });
            // 添加音乐文件 动作监听
            contextMenu.getItems().get(1).setOnAction(_ -> openMusicFile.run());
            // 添加音乐文件夹 动作监听
            contextMenu.getItems().get(2).setOnAction(_ -> openMusicFolder.run());
        } else {
            // 否则不显示
            contextMenu.hide();
        }
    }
}
