package com.yayangyang.glide44testdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Banner banner;
    private ArrayList<String> imageList=new ArrayList<>();
    private ArrayList<String> titleList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("w","onclick");
                if(banner.getContext()==MainActivity.this){
                    Log.e("是","ww");
                }
                Glide.get(MainActivity.this).clearMemory();
                System.runFinalization();
                System.gc();
            }
        });
        initData();
        initBaner(imageList,titleList);
    }

    private void initData() {
        imageList.add("http://images.dmzj.com/tuijian/750_480/171208xinman40tj5.jpg");
        imageList.add("http://images.dmzj.com/tuijian/750_480/171206youxitj1.jpg");
        imageList.add("http://images.dmzj.com/tuijian/750_480/171207benghuaitj1.jpg");
        imageList.add("http://images.dmzj.com/tuijian/750_480/1204kongxiang01.jpg");
        imageList.add("http://images.dmzj.com/tuijian/750_480/171208manzhantj2.jpg");

        for(int i=0;i<5;i++){
//            imageList.add("http://images.dmzj.com/webpic/8/blame.jpg");
        }
        for(int i=0;i<imageList.size();i++){
            titleList.add("wwwwwwwww"+i);
        }
    }

    private void initBaner(ArrayList images, ArrayList<String> titles) {
        banner=findViewById(R.id.banner);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(images);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(titles);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
    }
}
