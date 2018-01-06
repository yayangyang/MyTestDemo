package com.imooc.activitytest;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/10/9.
 */

public class LogUtil {

    public static void e(String str1,String str2){
        Log.e(str1,str2);
    }

    private static Context mContext;

    public static Context getmContext() {
        return mContext;
    }

    public static void setmContext(Context mContext) {
        LogUtil.mContext = mContext;
    }
}
