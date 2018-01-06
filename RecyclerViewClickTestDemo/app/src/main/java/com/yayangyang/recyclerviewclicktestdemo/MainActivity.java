package com.yayangyang.recyclerviewclicktestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//ScrollView和RecyclerView设置点击事件无响应,可能是View中的handleScrollBarDragging方法返回的变量导致
public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.recyclerView).setOnClickListener(this);
        findViewById(R.id.recyclerView).setOnTouchListener(this);
        findViewById(R.id.tv).setOnClickListener(this);
        findViewById(R.id.tv).setOnTouchListener(this);
        findViewById(R.id.scrollView).setOnClickListener(this);
        findViewById(R.id.scrollView).setOnTouchListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.recyclerView){
            Log.e("recyclerView","onClick");
        }else if(v.getId()==R.id.tv){
            Log.e("tv","onClick");
        }else if(v.getId()==R.id.tv){
            Log.e("scrollView","onClick");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId()==R.id.recyclerView){
            Log.e("recyclerView","onTouch");
        }else if(v.getId()==R.id.tv){
            Log.e("tv","onTouch");
        }else if(v.getId()==R.id.scrollView){
            Log.e("scrollView","onTouch");
        }
        return false;
    }
}
