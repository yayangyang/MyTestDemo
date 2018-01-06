package com.imooc.activitytest;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/10/9.
 */

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.e("SecondActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new AlertDialog.Builder(this)
                .setTitle("确认")
                .setMessage("确定吗？")
                .setPositiveButton("是", null)
                .setNegativeButton("否", null)
                .show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.e("SecondActivity","onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.e("SecondActivity","onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.e("SecondActivity","onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.e("SecondActivity","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.e("SecondActivity","onDestroy");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.e("SecondActivity","onRestart");
    }
}
