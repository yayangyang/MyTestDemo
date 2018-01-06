package com.imooc.videoplayer.bean;

/**
 * Created by Administrator on 2017/6/14.
 */

public class Lyric {
    private int startShowPosition;//歌词的开始显示位置
    private String text;//歌词内容

    public Lyric(int startShowPosition, String text) {
        this.startShowPosition = startShowPosition;
        this.text = text;
    }

    @Override
    public String toString() {
        return "Lyric{" +
                "startShowPosition=" + startShowPosition +
                ", lyric='" + text + '\'' +
                '}';
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getStartShowPosition() {
        return startShowPosition;
    }

    public void setStartShowPosition(int startShowPosition) {
        this.startShowPosition = startShowPosition;
    }
}
