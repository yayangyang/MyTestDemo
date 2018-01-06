package com.yayangyang.exceptiontestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void test() throws Exception{
        Log.e("wwwwwwwwwww","wwwwwwwwwwwwwwww1");
        int a=5/0;
        Log.e("wwwwwwwwwww","wwwwwwwwwwwwwwww2");
    }

}
