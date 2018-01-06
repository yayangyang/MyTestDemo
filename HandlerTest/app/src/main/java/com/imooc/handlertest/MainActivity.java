package com.imooc.handlertest;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private String a;
    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            if(msg.what==0x11){
                if(Looper.getMainLooper() == Looper.myLooper()){
                    Log.e("是否在主线程","在主线程");
                }else{
                    Log.e("是否在主线程","不在主线程");
                }
            }
            Log.e("ww",a);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mHandler.sendEmptyMessage(0x11);
        new Thread() {
            private Handler handler;
            public void run() {
                Log.e("ww","handler");
                Looper.prepare();
//                Looper.loop();
                handler = new Handler() {
                    public void handleMessage(Message msg) {
                        Log.e("TAG", "ww");
                    }
                };
                handler.sendEmptyMessage(0x11);
                Looper.loop();//放在handler新建前面不会收到消息(可能是线程执行到最后一行直接结束)
                mHandler.sendEmptyMessage(0x11);//发送消息到主线程
            }
        }.start();
    }
}
