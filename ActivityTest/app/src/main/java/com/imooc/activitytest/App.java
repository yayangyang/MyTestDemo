package com.imooc.activitytest;

import android.app.Application;

/**
 * Created by Administrator on 2017/10/9.
 */

public class App extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setmContext(this);
    }
}
