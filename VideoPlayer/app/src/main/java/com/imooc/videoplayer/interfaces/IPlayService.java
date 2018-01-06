package com.imooc.videoplayer.interfaces;

import com.imooc.videoplayer.bean.AudioItem;

/**
 * Created by Administrator on 2017/6/13.
 */

//播放服务接口
public interface IPlayService {
    void start();//播放
    void pause();//暂停
    void pre();//上一首
    void next();//下一首
    void openVideo();//打开一个音频
    boolean isPlaying();//是否正在播放
    int getCurrentPosition();//获取当前的播放位置
    int getDuration();//获取音频的总时长
    void seekTo(int progress);//跳转
    int switchPlayMode();//切换播放模式
    int getCurrentPlayMode();//获取当前的播放模式
    AudioItem getCurrentAudioItem();//获取当前的音频
}
