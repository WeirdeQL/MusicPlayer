package com.example.experimentmusicplayer.entity;

import android.content.Intent;
import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.List;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-19 9:34
 */
public class OnlineMusicItemEntity implements Serializable {

    private String name;
    private String update;
    private String info;
    private Bitmap picPath;
    private List<MusicEntity> musicEntities;

    public OnlineMusicItemEntity(String name, String update, String info, Bitmap picPath, List<MusicEntity> musicEntities) {
        this.name = name;
        this.update = update;
        this.info = info;
        this.picPath = picPath;
        this.musicEntities = musicEntities;
    }

    public OnlineMusicItemEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Bitmap getPicPath() {
        return picPath;
    }

    public void setPicPath(Bitmap picPath) {
        this.picPath = picPath;
    }

    public List<MusicEntity> getMusicEntities() {
        return musicEntities;
    }

    public void setMusicEntities(List<MusicEntity> musicEntities) {
        this.musicEntities = musicEntities;
    }

    @Override
    public String toString() {
        return "OnlineMusicItemEntity{" +
                "name='" + name + '\'' +
                ", update='" + update + '\'' +
                ", info='" + info + '\'' +
                ", picPath=" + picPath +
                ", musicEntities=" + musicEntities +
                '}';
    }
}
