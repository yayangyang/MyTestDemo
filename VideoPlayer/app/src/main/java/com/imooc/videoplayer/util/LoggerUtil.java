package com.imooc.videoplayer.util;

import android.content.Context;
import android.util.Log;

import static android.R.attr.tag;

/**
 * Created by Administrator on 2017/6/10.
 */

public class LoggerUtil {
    private static boolean isShowLog=true;

    //显示信息级别的日记
    public static void e(Object objTag,String msg){
        if(!isShowLog){
            return;
        }
        String tag=null;//如果是String,则直接使用,如果是其他对象,则使用这个对象的类名
        if(objTag instanceof String){
            tag=(String) objTag;
        }else if(objTag instanceof Class){
            tag=((Class) objTag).getSimpleName();
        }else{
            tag=objTag.getClass().getSimpleName();
        }
        Log.e(tag,msg);
    }
}
