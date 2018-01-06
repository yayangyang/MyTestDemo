package com.imooc.contexttest;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Administrator on 2017/11/6.
 */

public abstract class BaseActivity extends AppCompatActivity{
    protected Context mContext=this.getApplicationContext();//mBase未null所以报错
}
