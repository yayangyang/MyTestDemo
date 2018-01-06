package com.yayangyang.coordinatetestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class MyRecyclerView extends RecyclerView {

    private String TAG="MyRecyclerView";

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        Log.e(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
//        Log.e(TAG,"onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onDraw(Canvas canvas) {
//        Log.e(TAG,"onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
//        Log.e(TAG,"draw");
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
//        Log.e(TAG,"dispatchDraw");
        super.dispatchDraw(canvas);
    }

}
