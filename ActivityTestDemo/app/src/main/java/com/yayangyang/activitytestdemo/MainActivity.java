package com.yayangyang.activitytestdemo;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e(TAG,"handleMessage");
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e("MainActivity","onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Intent intent = new Intent();
//        intent.setAction("android.intent.action.share");
//
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            Uri contentUri = FileProvider.getUriForFile(this,
//                    BuildConfig.APPLICATION_ID + ".fileProvider",
//                    new File(Environment.getExternalStorageDirectory()+File.separator+"Pictures/p55.png"));
//            intent.setDataAndType(contentUri,"audio/*");
////            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
//        } else {
//            intent.setDataAndType(Uri.parse("file://abc"),"audio/*");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        }
//        startActivity(intent);

        mHandler.sendEmptyMessage(0);
    }

    @Override
    protected void onStart() {
        Log.e("MainActivity","onStart");
        super.onStart();
    }

    @Override
    protected void onRestart() {
        Log.e("MainActivity","onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume() {
        Log.e("MainActivity","onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.e("MainActivity","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.e("MainActivity","onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.e("MainActivity","onDestroy");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        Log.e("MainActivity","onSaveInstanceState");
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.e("MainActivity","onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

}
