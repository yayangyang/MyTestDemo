package com.imooc.test_handler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import static android.transition.Fade.IN;

/**
 * Created by Administrator on 2017/6/15.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setClass(this,MainActivity.class);
        startActivity(intent);
    }
}
