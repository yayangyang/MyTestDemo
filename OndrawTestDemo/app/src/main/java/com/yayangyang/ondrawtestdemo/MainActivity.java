package com.yayangyang.ondrawtestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyTextView myTextView = new MyTextView(this);
        Log.e("wwwwwwwwwwwwwwwwwww","wwwwwwwwww");
    }
}
