package com.yayangyang.meizudirectorytestdemo;

import android.app.Application;

/**
 * Created by Administrator on 2017/12/18.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.init(this);
    }
}
