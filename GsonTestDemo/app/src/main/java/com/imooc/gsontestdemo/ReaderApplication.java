package com.imooc.gsontestdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/10/27.
 */

public class ReaderApplication extends Application {
    private static Context sSInstance;

    public static Context getsInstance() {
        return sSInstance;
    }

}
