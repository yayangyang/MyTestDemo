package com.imooc.activitytest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LogUtil.e("MainActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setClass(this,SecondActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("MainActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("MainActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("MainActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("MainActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("MainActivity","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("SecondActivity","onRestart");
    }
}
