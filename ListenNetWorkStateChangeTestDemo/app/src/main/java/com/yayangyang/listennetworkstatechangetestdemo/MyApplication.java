package com.yayangyang.listennetworkstatechangetestdemo;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

/**
 * Created by Administrator on 2017/12/27.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        AppUtil.setContext(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        filter.addAction("android.net.wifi.STATE_CHANGE");
        registerReceiver(NetworkChangeReceiver.getInstance(), filter);
    }

}
