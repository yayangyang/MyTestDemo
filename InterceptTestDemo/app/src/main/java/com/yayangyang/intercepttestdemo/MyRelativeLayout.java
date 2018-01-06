package com.yayangyang.intercepttestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;
import android.widget.Scroller;

public class MyRelativeLayout extends RelativeLayout {

    private int count=0;
    private String TAG="MyRelativeLayout";

    @Override
    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
        super.requestDisallowInterceptTouchEvent(disallowIntercept);
    }

    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        Log.e(TAG,"onInterceptTouchEvent");
        count++;
        if(count>=100){
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void scrollBy(int x, int y) {
        super.scrollBy(x, y);
    }

}
