package com.yayangyang.intercepttestdemo;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

//被拦截的view最后响应的是cancel事件不会响应up事件,ViewGroup拦截它的子view事件后它的父布局仍可以拦截它的事件
public class MainActivity extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.root).setOnClickListener(this);
        findViewById(R.id.ll).setOnClickListener(this);
        findViewById(R.id.tv).setOnClickListener(this);

        findViewById(R.id.root).setOnTouchListener(this);
        findViewById(R.id.ll).setOnTouchListener(this);
        findViewById(R.id.tv).setOnTouchListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.root){
            Log.e("点击:","root");
        }else if(v.getId()==R.id.ll){
            Log.e("点击:","ll)");
        }else if(v.getId()==R.id.tv){
            Log.e("点击:","tv");
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        String str = check(v);
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(str.equals("TextView")){
                LinearLayout linearLayout = findViewById(R.id.ll);
                linearLayout.removeView(findViewById(R.id.tv));
            }
            Log.e("onTouch:"+str,"down");
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            Log.e("onTouch:"+str,"move");
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            Log.e("onTouch:"+str,"up");
        }else if(event.getAction()==MotionEvent.ACTION_CANCEL){
            Log.e("onTouch:"+str,"cancel");
        }
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    private String check(View v){
        String str="";
        if(v instanceof RelativeLayout){
            str="RelativeLayout";
        }else if(v instanceof LinearLayout){
            str="LinearLayout";
        }else if(v instanceof TextView){
            str="TextView";
        }
        return str;
    }

}
