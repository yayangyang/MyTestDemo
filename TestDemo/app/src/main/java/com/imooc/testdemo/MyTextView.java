package com.imooc.testdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/11/27.
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context) {
        super(context);
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("MyTextView","onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e("MyTextView","draw");
        super.draw(canvas);
    }
}
