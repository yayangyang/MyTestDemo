package com.imooc.videoplayer.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.adapter.MainAdapter;
import com.imooc.videoplayer.fragment.AudioFragment;
import com.imooc.videoplayer.fragment.VideoFragment;
import com.imooc.videoplayer.interfaces.Constants;
import com.imooc.videoplayer.interfaces.Keys;
import com.imooc.videoplayer.util.Lutil;
import com.imooc.videoplayer.util.Utils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import java.util.ArrayList;

import static android.R.attr.translationX;
import static android.R.attr.value;
import static java.security.AccessController.getContext;

public class MainActivity extends BaseActivity {
    private TextView tv_video,tv_audio;
    private View view_indicator;
    private ViewPager view_pager;
    @Override
    public int getLayoutResID() {
        Log.e("MainActivity","getLayoutResID");
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        check();
        tv_video= (TextView) findViewById(R.id.tv_video);
        tv_audio= (TextView) findViewById(R.id.tv_audio);
        view_indicator=findView(R.id.view_indicator);
        view_pager=findView(R.id.view_pager);
        initIndicator();
    }

    private void check() {
        int checkReadPermission = ContextCompat.
                checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        Lutil.e(checkReadPermission+"");
        if (checkReadPermission != PackageManager.PERMISSION_GRANTED) {
            requestPermission(0,Manifest.permission.READ_EXTERNAL_STORAGE,
                    new Runnable(){
                        @Override
                        public void run() {
                            Lutil.e("同意");
                        }
                    },
                    new Runnable(){
                        @Override
                        public void run() {
                            Lutil.e("拒绝");
                        }
                    });
        }
    }

    //初始化指示器
    private void initIndicator() {
        int screenWidth = Utils.getScreenWidth(this);
        view_indicator.getLayoutParams().width=screenWidth/2;
        view_indicator.requestLayout();//通知这个view去更新它的布局参数
    }

    @Override
    public void initListener() {
        tv_video.setOnClickListener(this);
        tv_audio.setOnClickListener(this);
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("positionOffset",positionOffset+"");
                scrollIndicator(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                changeTitleTextState(position==0);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    //滚动指示线
    private void scrollIndicator(int position, float positionOffset) {
        float translationX = view_indicator.getLayoutParams().width * (position + positionOffset);
        ViewHelper.setTranslationX(view_indicator,translationX);
    }

    @Override
    public void onclick(View v, int id) {
        if(v.getId()==R.id.tv_video){
            view_pager.setCurrentItem(0);
        }else if(v.getId()==R.id.tv_audio){
            view_pager.setCurrentItem(1);
        }
    }

    /**
     * 改变标题状态
     */
    private void changeTitleTextState(boolean isSelectvideo) {
        //改变文本颜色
        tv_video.setSelected(isSelectvideo);
        tv_audio.setSelected(!isSelectvideo);
        //改变文本大小
        scaleTitle(isSelectvideo?1.2f:1.0f,tv_video);
        scaleTitle(isSelectvideo?1.0f:1.2f,tv_audio);
    }

    private void scaleTitle(float scale,TextView textView){
        ViewPropertyAnimator.animate(tv_video).scaleX(scale).scaleY(scale);
    }

    @Override
    public void initData() {
        changeTitleTextState(true);
        initViewPager();
    }

    //初始化ViewPager
    private void initViewPager() {
        ArrayList<Fragment> fragments=new ArrayList<Fragment>();
        fragments.add(new VideoFragment());
        fragments.add(new AudioFragment());
        MainAdapter adapter=new MainAdapter(getSupportFragmentManager(),fragments);
        view_pager.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("MainActivity","onResume");
        if(SplashActivity.IS_FROM_AUDIO_PLAYER){
            //如果返回true,说明是从音乐列表回来的
            view_pager.setCurrentItem(1);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MainActivity","onDestroy");
    }
}
