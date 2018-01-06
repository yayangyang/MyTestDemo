package com.yayangyang.hierarchytestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("MyScrollView","onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("MyScrollView","onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e("MyScrollView","draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("MyScrollView","onDraw");
        super.onDraw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("MyScrollView","dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.e("MyScrollView","drawChild");
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public void invalidate() {
        Log.e("MyScrollView","invalidate");
        super.invalidate();
    }
}
