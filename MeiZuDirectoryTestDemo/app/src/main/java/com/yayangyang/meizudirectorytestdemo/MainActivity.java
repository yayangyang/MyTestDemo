package com.yayangyang.meizudirectorytestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("ww",Constant.PATH_DATA);
        FileUtils.createDir("/storage/emulated/0/meizudirectorytestdemo");
    }
}
