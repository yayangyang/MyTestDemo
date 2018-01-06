package com.imooc.menutest;

import android.app.Application;

public class ReaderApplication extends Application {

    private static ReaderApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
    }

    public static ReaderApplication getsInstance() {
        return sInstance;
    }

}
