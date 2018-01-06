package com.yayangyang.shareactivitytestdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by Administrator on 2017/12/30.
 */

public class ShareActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("ShareActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);
    }

    @Override
    protected void onStart() {
        Log.e("ShareActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e("ShareActivity","onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e("ShareActivity","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("ShareActivity","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("ShareActivity","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("ShareActivity","onDestroy");
        super.onDestroy();
    }
}
