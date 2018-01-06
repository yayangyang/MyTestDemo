package com.imooc.videoplayer.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.bean.AudioItem;
import com.imooc.videoplayer.bean.Lyric;
import com.imooc.videoplayer.interfaces.Constants;
import com.imooc.videoplayer.interfaces.IPlayService;
import com.imooc.videoplayer.interfaces.Keys;
import com.imooc.videoplayer.service.AudioPlayService;
import com.imooc.videoplayer.util.Lutil;
import com.imooc.videoplayer.util.Utils;
import com.imooc.videoplayer.view.lyricView;

import java.util.ArrayList;

import static android.R.attr.filter;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.imooc.videoplayer.R.id.sb_audio;
import static com.imooc.videoplayer.service.AudioPlayService.ACTION_UPDATE_UI;
import static com.imooc.videoplayer.service.AudioPlayService.PLAY_MODE_ORDER;
import static com.imooc.videoplayer.service.AudioPlayService.PLAY_MODE_RANDOM;
import static com.imooc.videoplayer.service.AudioPlayService.PLAY_MODE_SINGLE;

/**
 * Created by Administrator on 2017/6/13.
 */

public class AudioPlayerActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ServiceConnection mConn;
    private BroadcastReceiver mReceiver,mAudioReleaseReceiver;
    private IPlayService mPlayService;//播放服务的接口
    private Button mBtn_play;
    private TextView mTv_title;
    private TextView mTv_artist;
    private ImageView mIv_vision;
    private TextView mTv_play_time;
    private Button mBtn_pre;
    private Button mBtn_next;
    private static final int UPDATE_PLAY_TIME=1;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==UPDATE_PLAY_TIME){
                updatePlayTime();
            }
        }
    };
    private SeekBar mSb_audio;
    private Button mBtn_play_mode;
    private lyricView mLyric_view;
    public static final String ACTION_AUDIO_PAUSE = "audioPause";//音频暂停的广播

    @Override
    public int getLayoutResID() {
        return R.layout.activity_audio_player;
    }

    @Override
    public void initView() {
        mToolbar=findView(R.id.toolbar);
        mToolbar.setTitle("");
        mToolbar.setNavigationIcon(R.drawable.selector_btn_back);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(AudioPlayerActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mBtn_play = findView(R.id.btn_play);
        mTv_title = findView(R.id.tv_title);
        mTv_artist = findView(R.id.tv_artist);
        mTv_play_time = findView(R.id.tv_play_time);
        mBtn_pre = findView(R.id.btn_pre);
        mBtn_next = findView(R.id.btn_next);
        mIv_vision = findView(R.id.iv_vision);
        mSb_audio = findView(sb_audio);
        mBtn_play_mode = findView(R.id.btn_play_mode);
        mLyric_view = findView(R.id.lyric_view);
        AnimationDrawable animationDrawable = (AnimationDrawable) mIv_vision.getBackground();
        animationDrawable.start();
    }

    @Override
    public void initListener() {
        mSb_audio.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    //若不是判断当用户拖动进度条快进快退,
                    // 代码改变进度会频繁触发此方法导致音乐断断续续
                    mPlayService.seekTo(progress);
                }
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    @Override
    public void initData() {
        registerUpdateUIReceiver();
        registerAudioReleaseReceiver();
        connectService();
    }

    //注册更新UI的接收者
    private void registerUpdateUIReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AudioItem item= (AudioItem) intent.getSerializableExtra(Keys.ITEM);
                updateUI(item);
            }
        };
        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_UI));
    }

    //注册取消更新UI的接收者
    private void registerAudioReleaseReceiver() {
        mAudioReleaseReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                mHandler.removeCallbacksAndMessages(null);
            }
        };
        //若在这里填成mReceiver,报空指针错,AudioPlayService的mMediaPlayer不知为何会被置为空
        registerReceiver(mAudioReleaseReceiver,new IntentFilter(AudioPlayService.ACTION_AUDIO_RELEASE));
    }

    //连接服务
    private void connectService() {
        Intent intent = getIntent();
        ArrayList<AudioItem> audios= (ArrayList<AudioItem>) intent.getSerializableExtra(Keys.ITEMS);
        int currentPosition=intent.getIntExtra(Keys.CURRENT_POSITION,-1);

        Intent intentService=new Intent(this, AudioPlayService.class);
        final int what=intent.getIntExtra(Keys.WHAT,-1);
        intentService.putExtra(Keys.WHAT,what);
        intentService.putExtra(Keys.ITEMS,audios);
        intentService.putExtra(Keys.CURRENT_POSITION,currentPosition);

        //为了长期在后台运行,所以用startService,为了与服务进行交互所以用bindService
        startService(intentService);
        mConn = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder binder) {
                Log.e("onServiceConnected","onServiceConnected");
                //连接成功之后开始播放音乐
                mPlayService = ((AudioPlayService.MyBinder) binder).mPlayService;
                if(what==AudioPlayService.WHAT_ROOT){
                    //说明是从通知栏点击进来的
                    Log.e("mPlayService",mPlayService.getCurrentAudioItem()+"---");
                    updateUI(mPlayService.getCurrentAudioItem());
                }else{
                    mPlayService.openVideo();
                }
            }
            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.e("onServiceDisconnected","onServiceDisconnected");
            }
        };
        //服务与activity绑定,如果activity销毁则service销毁(除非用startService开启)
        //BIND_AUTO_CREATE表示没创建service就创建service
        bindService(intentService, mConn,BIND_AUTO_CREATE);
//        Log.e("ww","测试绑定服务是否运行在主线程");//应该不在主线程
        //绑定服务在子线程运行,主线程代码接下来执行onMeasure-onLayout-onDraw,此时与子线程抢占CPU执行
    }

//    @Override
//    public void onClick(View v) {
//        if(v.getId()== R.id.btn_back){//处理共同操作
//
//        }else{
//            //如果单击的不是返回按钮,则还是由子类去做处理
//            onclick(v,v.getId());
//        }
//    }

    @Override
    public void onclick(View v, int id) {
        if(id==R.id.btn_play){
            play();
        }else if(id==R.id.btn_pre){
            mPlayService.pre();
        }else if(id==R.id.btn_next){
            mPlayService.next();
        }else if(id==R.id.btn_play_mode){
            switchPlayMode();
        }
    }

    private void switchPlayMode() {
        int currentPlayMode=mPlayService.switchPlayMode();
        updatePlayModeBtnBg(currentPlayMode);
    }

    //更新播放模式的背景
    private void updatePlayModeBtnBg(int currentPlayMode) {
        int resid;
        if(currentPlayMode==AudioPlayService.PLAY_MODE_ORDER){
            resid=R.drawable.selector_btn_playmode_order;
        }else if(currentPlayMode==AudioPlayService.PLAY_MODE_SINGLE){
            resid=R.drawable.selector_btn_playmode_single;
        }else if(currentPlayMode==AudioPlayService.PLAY_MODE_RANDOM){
            resid=R.drawable.selector_btn_playmode_random;
        }else{
            resid=currentPlayMode;
            Lutil.e("见鬼了,当前播放模式为:"+currentPlayMode);
        }
        mBtn_play_mode.setBackgroundResource(resid);
    }

    private void play() {
        if(mPlayService.isPlaying()){
            mPlayService.pause();
        }else{
            mPlayService.start();
        }
        updatePlayBtnBg();
    }

    //更新播放按钮的背景
    private void updatePlayBtnBg() {
        int resid;
        if(mPlayService.isPlaying()){
            //如果当前是播放,则显示暂停按钮
            resid=R.drawable.selector_btn_audio_pause;
        }else{
            resid=R.drawable.selector_btn_audio_play;
        }
        mBtn_play.setBackgroundResource(resid);
}

    @Override
    protected void onDestroy() {
        Log.e("AudioPlayerActivity","onDestroy");
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        unbindService(mConn);
        unregisterReceiver(mReceiver);
        unregisterReceiver(mAudioReleaseReceiver);
    }

    //更新UI
    public void updateUI(AudioItem item){
//        Logger.e(this,"updateUI");
        updatePlayBtnBg();//更新播放按钮的背景
        mTv_title.setText(item.getTitle());//显示歌曲名称
        mTv_artist.setText(item.getArtist());//显示艺术家
        mSb_audio.setMax(mPlayService.getDuration());
        mLyric_view.setMusicPath(item.getData());
        updatePlayTime();
        updatePlayModeBtnBg(mPlayService.getCurrentPlayMode());
}

    private void updatePlayTime() {
//        Log.e("updatePlayTime",mPlayService.getCurrentPosition()+"");
//        if(mLyric_view!=null) Lutil.e("不为空");
        mTv_play_time.setText(Utils.formatMillis(mPlayService.getCurrentPosition())+"/"+
                Utils.formatMillis(mPlayService.getDuration()));
        mSb_audio.setProgress(mPlayService.getCurrentPosition());
        mLyric_view.updatePosition(mPlayService.getCurrentPosition());
        mHandler.sendEmptyMessageDelayed(UPDATE_PLAY_TIME,250);
    }

    @Override
    protected void onResume() {
        super.onResume();
        SplashActivity.IS_FROM_AUDIO_PLAYER=true;
    }
}
