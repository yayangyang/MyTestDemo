package com.imooc.testdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;

/**
 * Created by Administrator on 2017/10/23.
 */

public class MyRecyclerView extends RecyclerView {
    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        Log.e("MyRecyclerView","onMeasure");
        super.onMeasure(widthSpec, heightSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.e("MyRecyclerView","onLayout");
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onDraw(Canvas c) {
        Log.e("MyRecyclerView","onDraw");
        super.onDraw(c);
    }
}
