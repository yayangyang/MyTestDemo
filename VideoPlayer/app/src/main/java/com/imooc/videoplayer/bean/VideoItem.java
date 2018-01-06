package com.imooc.videoplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/10.
 */

public class VideoItem implements Serializable{
    private String title;
    private long duration;
    private long size;
    private String data;

    //把一个Cursor对象封装成一个VideoItem对象
    public static VideoItem fromCursor(Cursor cursor){
        VideoItem item=new VideoItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
        item.setDuration(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.DURATION)));
        item.setSize(cursor.getLong(cursor.getColumnIndex(MediaStore.Video.Media.SIZE)));
        item.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
        return item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
