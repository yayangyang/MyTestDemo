package com.imooc.notificationdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/13.
 */

public class DemoActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);
        TextView tv_info= (TextView) findViewById(R.id.tv_info);
        tv_info.setText(getIntent().getIntExtra("what", -1)+"");
    }
}
