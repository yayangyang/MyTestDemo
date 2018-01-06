package com.imooc.videoplayer.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.Message;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.bean.VideoItem;
import com.imooc.videoplayer.interfaces.Keys;
import com.imooc.videoplayer.util.Lutil;
import com.imooc.videoplayer.util.Utils;
import com.imooc.videoplayer.view.FullScreen_VideoView;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;
import java.util.Date;

import static android.R.attr.path;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static com.imooc.videoplayer.R.id.btn_fullscreen;
import static com.imooc.videoplayer.R.id.ll_top_ctrl;
import static com.imooc.videoplayer.R.id.tv_title;
import static com.imooc.videoplayer.R.id.view_brightness;
import static com.imooc.videoplayer.activity.AudioPlayerActivity.ACTION_AUDIO_PAUSE;

/**
 * Created by Administrator on 2017/6/11.
 */

public class VideoPlayerActivity extends BaseActivity{
    private FullScreen_VideoView mVideoView;
    private VideoItem currentVideo;
    private TextView tv_title,tv_system_time,tv_current_position,tv_duration;
    private ImageView iv_battery;
    private SeekBar sb_voice,sb_video;
    private Button btn_pre,btn_next,btn_play,btn_fullscreen;
    //显示系统时间
    private static final int SHOW_SYSTEM_TIME=0;
    //更新播放进度
    private static final int UPDATE_PLAY_PROGRESS=1;
    //隐藏控制面板
    public static final int HIDE_CTRL_LAYOUT=2;
    private BroadcastReceiver mBatteryChangedReceiver;
    private int mMaxVolume;
    private AudioManager mAudioManager;
    private int mCurrentVolume;
    private GestureDetector mGestureDetector;
    private boolean mIsDownLeft;//是否是在屏幕左边按下
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==SHOW_SYSTEM_TIME){
                showSystemTime();
            }else if(msg.what==UPDATE_PLAY_PROGRESS){
                updatePlayProgress();
            }else if(msg.what==HIDE_CTRL_LAYOUT){
                showOrHideCtrlLayout();
            }
        }
    };
    private View mView_brightness;
    private float mCurrentAlpha;
    private ArrayList<VideoItem> mVideos;
    private int mCurrentPosition;
    private boolean fullscreen = false;
    private LinearLayout mLl_top_ctrl,mLl_bottom_ctrl;
    private Uri mVideoUri;
    private LinearLayout ll_loading;
    private ProgressBar progressBar;

    @Override
    public int getLayoutResID() {
        return R.layout.adapter_video_player;
    }

    @Override
    public void initView() {
        mVideoView=findView(R.id.video_view);
        tv_title=findView(R.id.tv_title);
        iv_battery=findView(R.id.iv_battery);
        tv_system_time=findView(R.id.tv_system_time);
        tv_current_position=findView(R.id.tv_current_position);
        tv_duration=findView(R.id.tv_duration);
        sb_voice=findView(R.id.sb_voice);
        sb_video=findView(R.id.sb_video);
        btn_pre=findView(R.id.btn_pre);
        btn_next=findView(R.id.btn_next);
        btn_play=findView(R.id.btn_play);
        btn_fullscreen=findView(R.id.btn_fullscreen);
        mView_brightness = findView(R.id.view_brightness);
        mView_brightness.setVisibility(View.VISIBLE);
        ll_loading=findView(R.id.ll_loading);
        progressBar=findView(R.id.progressBar);
//        mView_brightness.setAlpha(0f);//API11之后才出现
        float alpha=0.0f;
        setBrightness(alpha);
        showSystemTime();
        initCtrlLayout();
    }

    //初始化控制面板
    private void initCtrlLayout() {
        mLl_top_ctrl = findView(R.id.ll_top_ctrl);
        mLl_bottom_ctrl = findView(R.id.ll_bottom_ctrl);
        //顶部控制栏的隐藏:Y方向移动控件的高度的负数
        mLl_top_ctrl.measure(0,0);//让系统主动测量view(高度够用的时候一般可用)
        ViewHelper.setTranslationY(mLl_top_ctrl,-mLl_top_ctrl.getMeasuredHeight());
        //底部控制栏的隐藏,Y方向移动控件的高度
        mLl_bottom_ctrl.measure(0,0);
        ViewHelper.setTranslationY(mLl_bottom_ctrl,mLl_bottom_ctrl.getMeasuredHeight());
    }

    //设置屏幕亮度
    private void setBrightness(float alpha) {
        ViewHelper.setAlpha(mView_brightness,alpha);//设置成完全透明
    }

    //显示系统时间
    private void showSystemTime() {
        tv_system_time.setText(DateFormat.format("kk:mm:ss",System.currentTimeMillis()));
        mHandler.sendEmptyMessageDelayed(SHOW_SYSTEM_TIME,1000);
    }

    @Override
    public void initListener() {
        mVideoView.setOnPreparedListener(mOnPreparedListener);
        mVideoView.setOnCompletionListener(mOnCompletionListener);
        mVideoView.setOnInfoListener(mOnInfoListener);
        sb_voice.setOnSeekBarChangeListener(mOnSeekBarChangeListener);
        sb_video.setOnSeekBarChangeListener(mOnVideoSeekBarChangeListener);
        mGestureDetector=new GestureDetector(this,mOnGestureListener);
    }

    @Override
    public void initData() {
        pauseMusic();
        mVideoUri = getIntent().getData();
        if(mVideoUri !=null){
            //从第三方应用跳转过来的
            mVideoView.setVideoURI(mVideoUri);
            btn_pre.setEnabled(false);
            btn_next.setEnabled(false);
        }else{
            //从视频列表跳转过来的
            mVideos = (ArrayList<VideoItem>) getIntent().getSerializableExtra(Keys.ITEMS);
            mCurrentPosition = getIntent().getIntExtra(Keys.CURRENT_POSITION,-1);
//            if (openVideo()) return;
            openVideo();
        }
        registerBatteryChangeReceiver();//注册的时候不管电量有无发生改变都会收到一个广播
        initVoice();
    }

    //打开一个视频
    private boolean openVideo() {
        if(mVideos ==null|| mVideos.isEmpty()|| mCurrentPosition ==-1){
            return true;
        }
        btn_pre.setEnabled(mCurrentPosition!=0);
        btn_next.setEnabled(mCurrentPosition!=mVideos.size()-1);
        currentVideo= mVideos.get(mCurrentPosition);
        String path=currentVideo.getData();
//        Log.e("path",path+"");
        ll_loading.setVisibility(View.VISIBLE);
        mVideoView.setVideoPath(path);
//        videoView.setMediaController(new MediaController(this));//设置默认控制栏
        return false;
    }

    //初始化音量
    private void initVoice() {
        mAudioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if(mAudioManager==null){Log.e("mAudioManager111111","为空");}
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mCurrentVolume = getStreamVolume();
        sb_voice.setMax(mMaxVolume);
        sb_voice.setProgress(mCurrentVolume);
    }

    //获取当前音量
    private int getStreamVolume() {
        if(mAudioManager==null){Log.e("mAudioManager","为空");}
        return mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    //注册电量改变的接收者
    private void registerBatteryChangeReceiver() {
        mBatteryChangedReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                //获取电量等级
                int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
//                Log.e("level",level+"");
                updateBatteryBg(level);
            }
        };
        IntentFilter filter=new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mBatteryChangedReceiver,filter);
    }

    //更新电量背景图片
    private void updateBatteryBg(int level) {
        int resid=R.drawable.ic_battery_0;
        if(level==0){
            resid=R.drawable.ic_battery_0;
        }else if(level>=100){
            resid=R.drawable.ic_battery_100;
        }else if(level>=80){
            resid=R.drawable.ic_battery_80;
        }else if(level>=60){
            resid=R.drawable.ic_battery_60;
        }else if(level>=40){
            resid=R.drawable.ic_battery_40;
        }else if(level>=20){
            resid=R.drawable.ic_battery_20;
        }else if(level>=10){
            resid=R.drawable.ic_battery_10;
        }
        iv_battery.setBackgroundResource(resid);
    }

    @Override
    public void onclick(View v, int id) {
        cancelHideCtrlLayoutMessage();
        if(v.getId()==R.id.btn_voice){//静音按钮
            mute();
        }else if(v.getId()==R.id.btn_exit){//退出按钮
            finish();
        }else if(v.getId()==R.id.btn_pre){//上一首按钮
            pre();
        }else if(v.getId()==R.id.btn_play){//播放按钮
            play();
        }else if(v.getId()==R.id.btn_next){//下一首按钮
            next();
        }else if(v.getId()==R.id.btn_fullscreen){//全屏按钮
            switchFullscreen();
        }
        sendHideCtrlLayoutMessage();
    }

    //在全屏和默认大小之间切换
    private void switchFullscreen() {
        mVideoView.changeScreenSize();
        updateFullscreenBtnBg();
    }

    //更新全屏按钮的背景
    private void updateFullscreenBtnBg() {
        if(mVideoView.isFullscreen()){
            //显示一个恢复默认大小按钮的背景
            btn_fullscreen.setBackgroundResource(R.drawable.selector_btn_defaultscreen);
        }else{
            //显示一个全屏大小按钮的背景
            btn_fullscreen.setBackgroundResource(R.drawable.selector_btn_fullscreen);
        }
    }

    //播放或者暂停
    private void play() {
        if(mVideoView.isPlaying()){
            //如果当前是播放的,则暂停
            mVideoView.pause();
        }else{
            //如果当前是暂停的,则播放
            mVideoView.start();
        }
        updatePlayBtnBg();
    }

    //更新播放按钮背景图片
    private void updatePlayBtnBg() {
        int resid;
        if(mVideoView.isPlaying()){
            //如果当前是播放的,则显示暂停按钮
            resid=R.drawable.selector_btn_pause;
        }else{
            //如果当前是暂停的,则显示播放按钮
            resid=R.drawable.selector_btn_play;
        }
        btn_play.setBackgroundResource(resid);
    }

    //上一首
    private void pre() {
        if(mCurrentPosition!=0){
            mCurrentPosition--;
            openVideo();
        }
    }

    //下一首
    private void next() {
        if(mVideos==null){
            return;
        }
        if(mCurrentPosition!=mVideos.size()-1){
            mCurrentPosition++;
            openVideo();
        }
    }

    //静音或者恢复原来的音量
    private void mute() {
        //如果当前音量大于0,则保存一下这个音量,然后设置为0
        if(getStreamVolume()>0){
            mCurrentVolume = getStreamVolume();
            setStreamVolume(0);
            sb_voice.setProgress(0);
        }else{
            //如果当前音量为0,则恢复原来保存的音量
            setStreamVolume(mCurrentVolume);
            sb_voice.setProgress(mCurrentVolume);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //把触摸事件传给手势监听器
        boolean result=mGestureDetector.onTouchEvent(event);
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            cancelHideCtrlLayoutMessage();
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            sendHideCtrlLayoutMessage();
        }
        return result;
    }

    //取消隐藏控制面板的消息
    private void cancelHideCtrlLayoutMessage() {
        mHandler.removeMessages(HIDE_CTRL_LAYOUT);
    }

    private GestureDetector.SimpleOnGestureListener mOnGestureListener=new GestureDetector.SimpleOnGestureListener(){
        @Override
        public void onLongPress(MotionEvent e) {
//            btn_play.performClick();//用代码的方式单击按钮
            Lutil.e("onLongPress");
            play();
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float distanceYY=e1.getY()-e2.getY();
            if(mIsDownLeft){
                //如果是在屏幕左边按下,则改变屏幕的亮度
                changeBrightness(distanceYY);
            }else{
                //如果是在屏幕右边按下,则改变音量值
                changeVolume(distanceYY);
            }
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e) {
            Lutil.e("onDown");
            mCurrentVolume=getStreamVolume();
            mIsDownLeft = e.getX() < Utils.getScreenWidth(VideoPlayerActivity.this)/2;
            mCurrentAlpha = ViewHelper.getAlpha(mView_brightness);
            return super.onDown(e);
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            switchFullscreen();
            return true;
        }

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            Lutil.e("onSingleTapConfirmed");
            showOrHideCtrlLayout();
            return true;
        }
    };

    ///显示或隐藏控制面板
    private void showOrHideCtrlLayout() {
        if(ViewHelper.getTranslationY(mLl_top_ctrl)==0){
            //如果控制面板原来是显示的,则隐藏
            //顶部控制栏的隐藏:Y方向移动控件的高度的负数
            ViewPropertyAnimator.animate(mLl_top_ctrl).translationY(-mLl_top_ctrl.getHeight());
            //底部控制栏的隐藏:Y方向移动控件的高度的负数
            ViewPropertyAnimator.animate(mLl_bottom_ctrl).translationY(mLl_bottom_ctrl.getHeight());
        }else{
            //如果控制面板原来是隐藏的,则显示
            //顶部控制栏的显示:Y方向移动控件的高度
            ViewPropertyAnimator.animate(mLl_top_ctrl).translationY(0);
            //底部控制栏的显示:Y方向移动控件的高度
            ViewPropertyAnimator.animate(mLl_bottom_ctrl).translationY(0);
            sendHideCtrlLayoutMessage();
        }
    }

    //发送隐藏控制面板的消息(5秒钟后执行)
    private void sendHideCtrlLayoutMessage() {
        cancelHideCtrlLayoutMessage();
        mHandler.sendEmptyMessageDelayed(HIDE_CTRL_LAYOUT,5000);
    }

    //改变屏幕亮度
    private void changeBrightness(float distanceY) {
        //1.onTouchEvent(处理触摸事件)
        //2.GestureDetector(手势识别)
        //3.计算在屏幕y方向的滑动距离(e1-e2)
        //4.计算滑动的距离等于多少对应的亮度值
        //a.计算亮度最大值与屏幕高的比例
        float scale=1.0f/Utils.getScreenHeight(VideoPlayerActivity.this);
        //b.计算滑动的距离等于多少对应的亮度值:移动距离x比例
        float moveAlpha= distanceY*scale;
        //5.在原来亮度的基础上加上计算出来的对应亮度值
        float resultAlpha=mCurrentAlpha+moveAlpha;
        //预防超出范围
        if(resultAlpha<0){
            resultAlpha=0;
        }else if(resultAlpha>0.8f){
            resultAlpha=0.8f;
        }
        Log.e("resultAlpha",resultAlpha+"");
        //6.使用这个亮度值
        setBrightness(resultAlpha);
    }

    //根据移动屏幕的距离改变音量值
    private void changeVolume(float distanceY) {
        //1.onTouchEvent(处理触摸事件)
        //2.GestureDetector(手势识别)
        //3.计算在屏幕y方向的滑动距离(e1-e2)
        //4.计算滑动的距离等于多少对应的音量值
        //a.计算音量最大值与屏幕高的比例
        float scale=((float)mMaxVolume)/ Utils.getScreenHeight(VideoPlayerActivity.this);
        //b.计算滑动的距离等于多少对应的音量值:移动距离x比例
        int moveVolume= (int) (distanceY*scale);
        //5.在原来音量的基础上加上计算出来的对应音量值
        int resultVolume=mCurrentVolume+moveVolume;
        //预防超出范围
        if(resultVolume<0){
            resultVolume=0;
        }else if(resultVolume>mMaxVolume){
            resultVolume=mMaxVolume;
        }
        Log.e("resultVolume",resultVolume+"");
        //6.使用这个音量值
        setStreamVolume(resultVolume);
        sb_voice.setProgress(resultVolume);
    }

    //监听是否卡顿的监听器
    MediaPlayer.OnInfoListener mOnInfoListener=new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            if(what==MediaPlayer.MEDIA_INFO_BUFFERING_START){//视频卡了正在缓冲
                Log.e("ww","MEDIA_INFO_BUFFERING_START");
                progressBar.setVisibility(View.VISIBLE);
                return true;
            }else if(what==MediaPlayer.MEDIA_INFO_BUFFERING_END){//缓冲结束
                Log.e("ww","MEDIA_INFO_BUFFERING_END");
                progressBar.setVisibility(View.GONE);
                return true;
            }
            return false;
        }
    };

    //缓冲更新的监听器
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener=new MediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(MediaPlayer mp, int percent) {
//            Log.e("percent",percent+"");
            updateVideoSecondaryProgress(percent);
        }
    };

    //更新视频的缓冲进度
    private void updateVideoSecondaryProgress(int percent) {
        float percentFloat=percent/100.0f;
        int setSecondaryProgress= (int) (mVideoView.getDuration()*percentFloat);
        sb_video.setSecondaryProgress(setSecondaryProgress);
    }

    private SeekBar.OnSeekBarChangeListener mOnVideoSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if(fromUser){
                mVideoView.seekTo(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            cancelHideCtrlLayoutMessage();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            sendHideCtrlLayoutMessage();
        }
    };

    private SeekBar.OnSeekBarChangeListener mOnSeekBarChangeListener=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {//进度发送改变
            if(fromUser){
                setStreamVolume(progress);
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {//开始拖动SeekBar
            cancelHideCtrlLayoutMessage();
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {//停止拖动SeekBar
            sendHideCtrlLayoutMessage();
        }
    };

    //视频播放完会回调这个监听器
    private MediaPlayer.OnCompletionListener mOnCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            mVideoView.seekTo(0);
            tv_current_position.setText(Utils.formatMillis(0));
            sb_video.setProgress(0);
        }
    };

    //设置音量
    private void setStreamVolume(int value) {
        int flags=0;//1-显示系统的音量浮动面板,0-不显示系统的音量浮动面板
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC,value,flags);
    }

    private MediaPlayer.OnPreparedListener mOnPreparedListener=new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.setOnBufferingUpdateListener(mBufferingUpdateListener);
            mVideoView.start();
            updatePlayBtnBg();
            if(mVideoUri!=null){
                //从第三方应用跳转过来的
                tv_title.setText(mVideoUri.getPath());//设置标题(uri的路径)
            }else{
                tv_title.setText(currentVideo.getTitle());//设置标题
            }
            tv_duration.setText(Utils.formatMillis(mVideoView.getDuration()));//显示视频总时长
            sb_video.setMax(mVideoView.getDuration());
            updatePlayProgress();
            hideLoading();
        }

    };

    //隐藏Loading界面,使用渐变的方式慢慢隐藏
    private void hideLoading() {
        ViewPropertyAnimator.
                animate(ll_loading)
                .alpha(0.0f)
                .setDuration(1000)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {}
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ll_loading.setVisibility(View.GONE);
                        ViewHelper.setAlpha(ll_loading,1.0f);
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {}
                    @Override
                    public void onAnimationRepeat(Animator animation) {}
                });
    }

    //更新播放进度
    private void updatePlayProgress() {
        tv_current_position.setText(Utils.formatMillis(mVideoView.getCurrentPosition()));//显示当前播放位置
        sb_video.setProgress(mVideoView.getCurrentPosition());
        mHandler.sendEmptyMessageDelayed(UPDATE_PLAY_PROGRESS,300);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        unregisterReceiver(mBatteryChangedReceiver);
        resumeMusic();
    }

    //使其他正在播放的声音暂停
    private void pauseMusic() {
        //暂停本播放器音乐
        Intent intent = new Intent(ACTION_AUDIO_PAUSE);
        sendBroadcast(intent);

        //暂停正在播放音乐(主要暂停其他播放器正在播放的音乐)
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        sendBroadcast(i);
    }

    private void resumeMusic() {
        //使本播放器暂停音乐继续播放
        Intent intent = new Intent(ACTION_AUDIO_PAUSE);
        sendBroadcast(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashActivity.IS_FROM_AUDIO_PLAYER=false;
    }
}
