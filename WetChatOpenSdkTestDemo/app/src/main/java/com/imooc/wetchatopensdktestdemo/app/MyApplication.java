package com.imooc.wetchatopensdktestdemo.app;

import android.app.Application;

import com.imooc.wetchatopensdktestdemo.utils.AppUtils;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2017/11/9.
 */

public class MyApplication extends Application {

    private static MyApplication sInstance;
    public static final String APP_ID = "wxd930ea5d5a258f4f";

    public static IWXAPI mWxApi;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance=this;
        AppUtils.init(this);
        registToWX();
    }

    private void registToWX() {
        mWxApi = WXAPIFactory.createWXAPI(this,APP_ID , false);
        // 将该app注册到微信
        mWxApi.registerApp(APP_ID);
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }

}
