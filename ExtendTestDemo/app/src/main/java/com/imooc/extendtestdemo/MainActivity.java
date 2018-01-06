package com.imooc.extendtestdemo;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements SecondInterface{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        View viewById = findViewById(R.id.tv);
        Log.e("viewById","ww"+viewById);
    }

    @Override
    public void test(String a) {

    }
}
