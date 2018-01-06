package com.yayangyang.hierarchytestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RelativeLayout;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MyRelativeLayout extends RelativeLayout {
    public MyRelativeLayout(Context context) {
        super(context);
    }

    public MyRelativeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("MyRelativeLayout","onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("MyRelativeLayout","onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("MyRelativeLayout","onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e("MyRelativeLayout","draw");
        super.draw(canvas);
        Log.e("MyRelativeLayout","draw");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("MyRelativeLayout","dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        Log.e("MyRelativeLayout","drawChild");
        return super.drawChild(canvas, child, drawingTime);
    }

    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        Log.e("MyRelativeLayout","invalidateChildInParent");
        return super.invalidateChildInParent(location, dirty);
    }
}
