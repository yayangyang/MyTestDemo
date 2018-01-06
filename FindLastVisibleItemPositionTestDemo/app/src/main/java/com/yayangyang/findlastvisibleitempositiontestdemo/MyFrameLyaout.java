package com.yayangyang.findlastvisibleitempositiontestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

/**
 * Created by Administrator on 2017/12/25.
 */

public class MyFrameLyaout extends FrameLayout {

    public MyFrameLyaout(@NonNull Context context) {
        super(context);
    }

    public MyFrameLyaout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
//        Log.e("MyFrameLyaout","onMeasure");
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.e("MyFrameLyaout","onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onDraw(Canvas c) {
        Log.e("MyFrameLyaout","onDraw");
        super.onDraw(c);
    }

}
