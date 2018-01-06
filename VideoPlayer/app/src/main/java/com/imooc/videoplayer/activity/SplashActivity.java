package com.imooc.videoplayer.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.imooc.videoplayer.R;

import java.util.Timer;

import static android.R.attr.delay;

/**
 * Created by Administrator on 2017/6/10.
 */

public class SplashActivity extends BaseActivity {
    private Handler handler;
    //判断是否从从音乐列表返回主界面
    public static boolean IS_FROM_AUDIO_PLAYER=false;
    @Override
    public int getLayoutResID() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        delayEnterHome();
    }

    /**
     * 延迟3秒钟进入首页
     */
    private void delayEnterHome() {
        handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                enterHome();
            }
        },3000);
//        Log.e("ww",Thread.activeCount()+"dd");//网上说这里没有开启新线程,只是放到队列里
    }

    private void enterHome() {
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onclick(View v, int id) {

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            handler.removeCallbacksAndMessages(null);
            enterHome();
        }
        return super.onTouchEvent(event);
    }
}
