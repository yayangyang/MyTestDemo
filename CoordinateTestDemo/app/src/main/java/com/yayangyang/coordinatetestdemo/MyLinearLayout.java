package com.yayangyang.coordinatetestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewParent;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2018/1/2.
 */

public class MyLinearLayout extends LinearLayout {

    private String TAG="MyLinearLayout";

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e(TAG,"onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e(TAG,"onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onDraw(Canvas canvas) {
        Log.e(TAG,"onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e(TAG,"draw");
        super.draw(canvas);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e(TAG,"dispatchDraw");
        super.dispatchDraw(canvas);
    }

    @Override
    public ViewParent invalidateChildInParent(int[] location, Rect dirty) {
        Log.e(TAG,"invalidateChildInParent");
        return super.invalidateChildInParent(location, dirty);
    }

    @Override
    public void requestLayout() {
        Log.e(TAG,"requestLayout");
        super.requestLayout();
    }
}
