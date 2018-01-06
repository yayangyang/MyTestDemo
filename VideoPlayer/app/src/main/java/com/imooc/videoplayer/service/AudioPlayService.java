package com.imooc.videoplayer.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.BatteryManager;
import android.os.Binder;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.activity.AudioPlayerActivity;
import com.imooc.videoplayer.bean.AudioItem;
import com.imooc.videoplayer.interfaces.IPlayService;
import com.imooc.videoplayer.interfaces.Keys;
import com.imooc.videoplayer.util.LoggerUtil;
import com.imooc.videoplayer.util.Lutil;
import com.imooc.videoplayer.util.SpUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Logger;

import static com.imooc.videoplayer.R.id.btn_next;
import static com.imooc.videoplayer.R.id.btn_pre;
import static com.imooc.videoplayer.R.id.ll_loading;

/**
 * Created by Administrator on 2017/6/13.
 */

public class AudioPlayService extends Service implements IPlayService{
    //更新UI的广播Action
    public static final String ACTION_UPDATE_UI="updateUI";
    public static final String ACTION_AUDIO_RELEASE = "audioRelease";//音频释放停止更新UI
    private ArrayList<AudioItem> mAudios;
    private int mCurrentPosition;
    private MediaPlayer mMediaPlayer;
    private AudioItem mCurrentAudio;
    public static final int PLAY_MODE_ORDER=1;//顺序播放
    public static final int PLAY_MODE_SINGLE=2;//单曲播放
    public static final int PLAY_MODE_RANDOM=3;//随机播放
    private int currentPlayMode;//当前的播放模式
    private Random mRandom;
    private NotificationManager mNotificationManager;
    private int notifiId=1;
    private BroadcastReceiver mPauseMusicReceiver;

    @Override
    public void onCreate() {
        LoggerUtil.e(this,"onCreate");
        mRandom = new Random();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        registerPauseMusicReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("AudioPlayService","onStartCommand");
        currentPlayMode= SpUtil.getInt(this,Keys.CURRENT_PLAY_MODE,PLAY_MODE_ORDER);

        if(intent!=null){
            int what = intent.getIntExtra(Keys.WHAT, -1);
            if(what==WHAT_PRE){
                pre();
            }else if(what==WHAT_NEXT){
                next();
            }else if(what==WHAT_ROOT){

            }else{
                mAudios = (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEMS);
                mCurrentPosition = intent.getIntExtra(Keys.CURRENT_POSITION,-1);
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("AudioPlayService","onBind");
        MyBinder binder=new MyBinder();
        binder.mPlayService=this;
        return binder;
    }

    public class MyBinder extends Binder {
        public IPlayService mPlayService;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("AudioPlayService","onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.e("onDestroy","onDestroy");
    }

    @Override
    public void openVideo() {
        if(mAudios ==null|| mAudios.isEmpty()|| mCurrentPosition ==-1){
            return;
        }
        mCurrentAudio = mAudios.get(mCurrentPosition);

        //暂停正在播放音乐(主要暂停其他播放器正在播放的音乐)
        Intent i = new Intent("com.android.music.musicservicecommand");
        i.putExtra("command", "pause");
        sendBroadcast(i);

        release();
        try {
            mMediaPlayer=new MediaPlayer();
            Log.e("openVideo",mMediaPlayer+"eeee");
            mMediaPlayer.setOnPreparedListener(mPreparedListener);
            mMediaPlayer.setOnCompletionListener(mCompletionListener);
//            Lutil.e(mCurrentAudio.getData());
//            mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mMediaPlayer.setDataSource(mCurrentAudio.getData());
            mMediaPlayer.prepareAsync();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void release() {
        if (mMediaPlayer != null) {
            notifyReleaseUI();
            mMediaPlayer.reset();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void notifyReleaseUI() {
        sendBroadcast(new Intent(ACTION_AUDIO_RELEASE));
    }

    public void start(){
        if(mMediaPlayer!=null){
            sendNotificaction();
//            Log.e("wwwww","sendNotificaction");
            mMediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if(mMediaPlayer!=null){
            mNotificationManager.cancel(notifiId);
            mMediaPlayer.pause();
        }
    }

    @Override
    public void pre() {
        if(mMediaPlayer==null){
            return;
        }
        if(currentPlayMode==PLAY_MODE_ORDER){
            if(mAudios!=null){
                if(mCurrentPosition!=0){
                    mCurrentPosition--;
                }else{
                    mCurrentPosition=mAudios.size()-1;
                }
            }
        }else if(currentPlayMode==PLAY_MODE_SINGLE){

        }else if(currentPlayMode==PLAY_MODE_RANDOM){
            mCurrentPosition=mRandom.nextInt(mAudios.size());
        }else{
            Lutil.e("见鬼了,当前播放模式为:"+currentPlayMode);
        }
        openVideo();
    }

    @Override
    public void next() {
        if(mMediaPlayer==null){
            Log.e("mMediaPlayer",mMediaPlayer+"");
            return;
        }
        if(currentPlayMode==PLAY_MODE_ORDER){
            if(mAudios!=null){
                if(mCurrentPosition!=mAudios.size()-1){
                    mCurrentPosition++;
                }else{
                    mCurrentPosition=0;
                }
            }
        }else if(currentPlayMode==PLAY_MODE_SINGLE){

        }else if(currentPlayMode==PLAY_MODE_RANDOM){
            mCurrentPosition=mRandom.nextInt(mAudios.size());
        }else{
            Lutil.e("见鬼了,当前播放模式为:"+currentPlayMode);
        }
        openVideo();
    }

    @Override
    public boolean isPlaying() {
        if(mMediaPlayer!=null){
            return mMediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public int getCurrentPosition() {
        if(mMediaPlayer!=null){
            return mMediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    @Override
    public int getDuration() {
        if(mMediaPlayer!=null){
            return mMediaPlayer.getDuration();
        }
        return 0;
    }

    @Override
    public void seekTo(int progress) {
        if(mMediaPlayer!=null){
            mMediaPlayer.seekTo(progress);
        }
    }

    @Override
    public int switchPlayMode() {
        if(currentPlayMode==PLAY_MODE_ORDER){//如果当前是顺序播放,则切换为单曲播放
            currentPlayMode=PLAY_MODE_SINGLE;
        }else if(currentPlayMode==PLAY_MODE_SINGLE){//如果当前是顺序播放,则切换为随机播放
            currentPlayMode=PLAY_MODE_RANDOM;
        }else if(currentPlayMode==PLAY_MODE_RANDOM){//如果当前是顺序播放,则切换为单曲播放
            currentPlayMode=PLAY_MODE_ORDER;
        }else{
            Lutil.e("见鬼了,当前播放模式为:"+currentPlayMode);
        }
        SpUtil.putInt(this,Keys.CURRENT_PLAY_MODE,currentPlayMode);
        return currentPlayMode;
    }

    @Override
    public int getCurrentPlayMode() {
        return currentPlayMode;
    }

    @Override
    public AudioItem getCurrentAudioItem() {
        return mCurrentAudio;
    }

    private MediaPlayer.OnPreparedListener mPreparedListener=new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            start();
            notifyUpdateUI();
        }
    };

    private MediaPlayer.OnCompletionListener mCompletionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            Log.e("onCompletion","onCompletion");
            next();
        }
    };

    //通知界面更新
    private void notifyUpdateUI() {
        Intent intent = new Intent(ACTION_UPDATE_UI);
        intent.putExtra(Keys.ITEM,mCurrentAudio);
        sendBroadcast(intent);
    }

    //发送通知
    private void sendNotificaction() {
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_notification)//提示icon(没有设置自定义通知布局时图片显示不出)
                .setTicker("当前正在播放:")//提示文本
                .setOngoing(false)// 设置通知无法划掉
                .setContentTitle(mCurrentAudio.getTitle())//设置通知标题
                .setContentText(mCurrentAudio.getArtist())//设置通知内容
                .setContentIntent(getActivityPendingIntent(WHAT_ROOT))
                .setWhen(System.currentTimeMillis())//通知时间
                .setContent(getRemoteView());//设置自定义通知的布局
        //参数1:如果id相同,则第二次不能发送
        mNotificationManager.notify(notifiId,builder.build());
    }

    public static final int WHAT_PRE=1;
    public static final int WHAT_NEXT=2;
    public static final int WHAT_ROOT=3;

    private RemoteViews getRemoteView() {
        RemoteViews remoteViews=new RemoteViews(getPackageName(), R.layout.notification);
        remoteViews.setTextViewText(R.id.tv_title,mCurrentAudio.getTitle());
        remoteViews.setTextViewText(R.id.tv_artist,mCurrentAudio.getArtist());
        remoteViews.setOnClickPendingIntent(R.id.btn_pre,getServicePendingIntent(WHAT_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next,getServicePendingIntent(WHAT_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.ll_root,getActivityPendingIntent(WHAT_ROOT));
        return remoteViews;
    }

    //获取activity类型的PendingIntent
    private PendingIntent getActivityPendingIntent(int what) {
        int requestCode=what;//用于判断获取的PendingIntent是否是同一个(同一个不会重复创建PendingIntent对象)
        Intent intent = new Intent(this,AudioPlayerActivity.class);
        intent.putExtra(Keys.WHAT,what);
        //PendingIntent.FLAG_UPDATE_CURRENT表示更新intent中的数据
        return PendingIntent
                .getActivity(this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //获取service类型的PendingIntent
    private PendingIntent getServicePendingIntent(int what) {
        int requestCode=what;//用于判断获取的PendingIntent是否是同一个(同一个不会重复创建PendingIntent对象)
        Intent intent = new Intent(this,AudioPlayService.class);
        intent.putExtra(Keys.WHAT,what);
        //PendingIntent.FLAG_UPDATE_CURRENT表示更新intent中的数据
        return PendingIntent
                .getService(this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

    //注册暂停/恢复播放音乐接收者
    private void registerPauseMusicReceiver() {
        mPauseMusicReceiver=new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(mMediaPlayer.isPlaying()){
                    pause();
                }else{
                    start();
                }
            }
        };
        IntentFilter filter=new IntentFilter(AudioPlayerActivity.ACTION_AUDIO_PAUSE);
        registerReceiver(mPauseMusicReceiver,filter);
    }

}
