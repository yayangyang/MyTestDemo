package com.imooc.videoplayer.bean;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;

import static android.R.attr.duration;

/**
 * Created by Administrator on 2017/6/10.
 */

public class AudioItem implements Serializable{
    private String title;
    private String artist;
    private String data;

    //把一个Cursor对象封装成一个VideoItem对象
    public static AudioItem fromCursor(Cursor cursor){
        AudioItem item=new AudioItem();
        item.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.TITLE)));
        item.setArtist(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.ARTIST)));
        item.setData(cursor.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)));
        return item;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
