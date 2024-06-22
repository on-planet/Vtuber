package org.java_course.java_course_project_aivtuber.GUI.Listener;

import javafx.scene.image.Image;

import static org.java_course.java_course_project_aivtuber.Application.*;

public class MetaDataListener implements Runnable {
    @Override
    public void run() {
        System.out.println(currentPlayerIndex);
        endTime = players.get(currentPlayerIndex).getStopTime().toSeconds();
        //设置music_name标签
        music_name.setText(STR."歌名：\{(String) players.get(currentPlayerIndex).getMedia().getMetadata().get("title")}");
        //设置artist标签
        artist.setText(STR."作者：\{(String) players.get(currentPlayerIndex).getMedia().getMetadata().get("artist")}");
        //设置album标签
        album.setText(STR."专辑：\{(String) players.get(currentPlayerIndex).getMedia().getMetadata().get("album")}");
        //在imageView_music_image添加image
        imageView_music_image.setImage((Image) players.get(currentPlayerIndex).getMedia().getMetadata().get("image"));
        if (players.get(currentPlayerIndex).getMedia().getMetadata().get("title") == null) {
            music_name.setText("未知歌名");
        }
        if (players.get(currentPlayerIndex).getMedia().getMetadata().get("artist") == null) {
            artist.setText("未知作者");
        }
        if (players.get(currentPlayerIndex).getMedia().getMetadata().get("album") == null) {
            artist.setText("未知专辑");
        }
        if (players.get(currentPlayerIndex).getMedia().getMetadata().get("image") == null) {
            imageView_music_image.setImage(file_image);
        }
    }
}
