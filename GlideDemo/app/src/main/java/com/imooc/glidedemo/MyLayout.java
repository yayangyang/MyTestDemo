package com.imooc.glidedemo;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;

/**
 * Created by Administrator on 2017/9/8.
 */

public class MyLayout extends LinearLayout {

    private ViewTarget<MyLayout, GlideDrawable> viewTarget;

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        viewTarget = new ViewTarget<MyLayout, GlideDrawable>(this) {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                MyLayout myLayout = getView();
                myLayout.setImageAsBackground(resource);
            }
        };
    }

    public ViewTarget<MyLayout, GlideDrawable> getTarget() {
        return viewTarget;
    }

    public void setImageAsBackground(GlideDrawable resource) {
        setBackground(resource);
    }

}