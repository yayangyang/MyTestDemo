package com.imooc.extendtestdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/10/11.
 */

public class BaseActivity extends AppCompatActivity implements FirstInterface{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        View viewById = findViewById(R.id.tv);
        Log.e("viewById","ww"+viewById);
    }



    @Override
    public void test(int a) {

    }
}
