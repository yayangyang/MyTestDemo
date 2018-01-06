package com.imooc.changelightdemo.utils;

import android.content.Context;

import com.imooc.changelightdemo.app.MyApp;

/**
 * Created by Administrator on 2017/9/19.
 */

public class AppUtils {
    private static Context mContext;
    public static void setContext(Context context){
        mContext=context;
    }

    public static Context getAppContext() {
        return mContext;
    }
}
