package com.imooc.videoplayer.interfaces;

import android.view.View;

/**
 * Created by Administrator on 2017/6/10.
 */

public interface UiOperation extends View.OnClickListener {
    //返回一个用于显示界面布局id
    int getLayoutResID();

    //初始化view
    void initView() ;

    //初始化监听器
    void initListener();

    //初始化数据
    void initData();

    //单机事件在这个方法中处理
    void onclick(View v, int id);
}
