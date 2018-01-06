package com.yayangyang.bannertestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.youth.banner.Banner;

//在xml加了banner这个控件内存增加了10mb
public class MainActivity extends AppCompatActivity {

//    private Banner mBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        mBanner=findViewById(R.id.banner);
    }
}
