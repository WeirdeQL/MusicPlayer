package com.example.experimentmusicplayer.entity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @author weirdo 灵雀丘
 * @version 1.0
 * @date 2020-05-12 9:35
 */
public class MusicEntity implements Serializable {

    private static final long serialVersionUID = 8604494830368708636L;
    private byte[] bytes;
    private Integer albumId;//专辑id
    private String album;//专辑名
    private String musicId;//音乐id
    private String displayName;//音乐文件名
    private String title;//歌曲名
    private Integer displayTime;//音乐时长
    private String artist;//歌手名字
    private String path;//文件路径
    private String isMusic;//是否为音乐


    public Integer getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Integer albumId) {
        this.albumId = albumId;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDisplayTime() {
        return displayTime;
    }

    public void setDisplayTime(Integer displayTime) {
        this.displayTime = displayTime;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getIsMusic() {
        return isMusic;
    }

    public void setIsMusic(String isMusic) {
        this.isMusic = isMusic;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public MusicEntity(byte[] bytes, Integer albumId, String album, String musicId, String displayName, String title, Integer displayTime, String artist, String path, String isMusic) {
        this.bytes = bytes;
        this.albumId = albumId;
        this.album = album;
        this.musicId = musicId;
        this.displayName = displayName;
        this.title = title;
        this.displayTime = displayTime;
        this.artist = artist;
        this.path = path;
        this.isMusic = isMusic;
    }

    public MusicEntity() {

    }

    @Override
    public String toString() {
        return "MusicEntity{" +
                ", albumId=" + albumId +
                ", album='" + album + '\'' +
                ", musicId='" + musicId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", title='" + title + '\'' +
                ", displayTime=" + displayTime +
                ", artist='" + artist + '\'' +
                ", path='" + path + '\'' +
                ", isMusic='" + isMusic + '\'' +
                '}';
    }

    //bitmap转byte
    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream =
                new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        return outputStream.toByteArray();
    }

    //byte转bitmap
    public static Bitmap getBitmapFromArrayBytes(byte[] bytes){
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public Bitmap getPic(){
        return getBitmapFromArrayBytes(this.bytes);
    }

    public void setPic(Bitmap bitmap){
        this.bytes=getBytesFromBitmap(bitmap);

    }
}
