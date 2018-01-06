package com.imooc.test_handler;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    //新建handler后源码里会取得handler这个对象，这样才能调用里面的方法
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            xunhuan();
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        xunhuan();
    }

    public void xunhuan(){
        Log.e("handleMessage","handleMessage");
        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
