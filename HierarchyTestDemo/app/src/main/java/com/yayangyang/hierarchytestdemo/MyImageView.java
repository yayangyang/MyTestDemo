package com.yayangyang.hierarchytestdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.util.Log;

/**
 * Created by Administrator on 2017/11/30.
 */

public class MyImageView extends AppCompatImageView {

    public MyImageView(Context context) {
        super(context);
    }

    public MyImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void draw(Canvas canvas) {
        Log.e("MyImageView","draw");
        super.draw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.e("MyImageView","onDraw");
        super.onDraw(canvas);
    }

    @Override
    public void invalidate() {
        Log.e("MyImageView","invalidate");
        super.invalidate();
    }
}
