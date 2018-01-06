package com.yayangyang.hierarchytestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/11/28.
 */

public class MyLinearLayout extends LinearLayout {

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.e("MyLinearLayout","onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("MyLinearLayout","onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("MyLinearLayout","onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        //draw不会被调用,但是ViewGroup的drawChild里调用的方法是三个参数的draw,点击跳转到View的draw方法(一个参数),
        //调用了draw的三个参数的方法
        Log.e("MyLinearLayout","draw");
        super.draw(canvas);
        Log.e("MyLinearLayout","draw");
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        Log.e("MyLinearLayout","dispatchDraw");
        //一般由draw方法调用
        super.dispatchDraw(canvas);
    }

    @Override
    public void invalidate() {
        Log.e("MyLinearLayout","invalidate");
        super.invalidate();
    }

}
