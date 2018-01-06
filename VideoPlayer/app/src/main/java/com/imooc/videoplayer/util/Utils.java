package com.imooc.videoplayer.util;

import android.content.Context;
import android.database.Cursor;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.imooc.videoplayer.activity.MainActivity;
import com.imooc.videoplayer.interfaces.Constants;

import java.util.Calendar;

import static android.R.attr.value;

/**
 * Created by Administrator on 2017/6/10.
 */

public class Utils {
    private static Object sScreenWidth;

    //查找并设置单击监听器
    public static void setButtonOnClickListener(View view, View.OnClickListener listener) {
        //遍历view里面所有的Button和ImageButton
        if(view instanceof Button ||view instanceof ImageButton){
            view.setOnClickListener(listener);
        }else if(view instanceof ViewGroup){
            ViewGroup viewGroup= (ViewGroup) view;
            for(int i=0;i<viewGroup.getChildCount();i++){
                View child=viewGroup.getChildAt(i);
                setButtonOnClickListener(child,listener);
            }
        }
    }

    //在屏幕中间显示一个Toast
    public static void showToast(String text, Context context){
        Toast toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    public static int getScreenWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getWidth();
    }

    public static int getScreenHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        return windowManager.getDefaultDisplay().getHeight();
    }

    //遍历cursor中所有的记录
    public static void printCursor(Cursor cursor) {
        if(cursor==null){
            return;
        }
        Lutil.e("共有"+cursor.getCount()+"条数据");
        //遍历所有的行
        while(cursor.moveToNext()){
            Lutil.e("eeeeeeeeeeeeeeee");
            Lutil.e("---------------------");
            //遍历所有的列
            for(int i=0;i<cursor.getColumnCount();i++){
                String columnName=cursor.getColumnName(i);
                String value=cursor.getString(i);
                Lutil.e(columnName+"="+value);
            }
        }
    }

    //格式化一个毫秒值,如果有小时,则格式化为时分秒,如:02:30:59,如果没有小时,格式化为分秒30:59
    public static CharSequence formatMillis(long duration) {
        //把duration转换为一个日期
        Calendar calendar=Calendar.getInstance();
        calendar.clear();//清除时间(1970年1月1日)
        calendar.add(Calendar.MILLISECOND, (int) duration);
        CharSequence inFormat=duration/ Constants.HOUR_MILLIS>0?"kk:mm:ss":"mm:ss";
        return DateFormat.format(inFormat,calendar.getTime());
    }
}
