package com.yayangyang.garbagecollectiontestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.lang.ref.WeakReference;

//对象引用为空,但是其方法正在执行不会被回收
public class MainActivity extends AppCompatActivity {

    public Test mTest=new Test();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTest.setActivity(this);
        mTest.clear();
        Log.e("mTest","为空");

//        System.gc();
        System.runFinalization();
        System.gc();
    }
}
