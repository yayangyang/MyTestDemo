package com.imooc.test_dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class MyAdvertisementView extends Dialog implements View.OnClickListener {

    public MyAdvertisementView(Context context) {
        super(context);
        setContentView(R.layout.view_dialog_advertisement);
        //设置点击布局外则Dialog消失
        setCanceledOnTouchOutside(true);
    }

    public void showDialog() {
        Window window = getWindow();
        //设置弹窗动画
        window.setWindowAnimations(R.style.style_dialog);
        //设置Dialog背景色
        window.setBackgroundDrawableResource(R.color.colorTransparent);
        WindowManager.LayoutParams wl = window.getAttributes();
        //设置弹窗位置
        wl.gravity = Gravity.CENTER;
        window.setAttributes(wl);
        show();
        findViewById(R.id.iv_advertisement).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}