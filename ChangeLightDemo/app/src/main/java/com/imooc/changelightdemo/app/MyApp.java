package com.imooc.changelightdemo.app;

import android.app.Application;
import android.content.Context;

import com.imooc.changelightdemo.utils.AppUtils;

/**
 * Created by Administrator on 2017/9/19.
 */

public class MyApp extends Application{

//    private static MyApp mMyApp;
//    //直接new一个继承自Application的对象getApplicationContext方法取不到context
//    public Context getMyContext() {
//        Context applicationContext = getApplicationContext();
//        return applicationContext;
//    }
//
//    public static MyApp getInstance(){
//        if(mMyApp==null){
//            mMyApp=new MyApp();
//        }
//        return mMyApp;
//    }

    public static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();

        AppUtils.setContext(applicationContext);
    }
}
