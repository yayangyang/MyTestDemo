package com.imooc.videoplayer.activity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.interfaces.UiOperation;
import com.imooc.videoplayer.util.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/6/10.
 */

public abstract class BaseActivity extends AppCompatActivity implements UiOperation{

    private Map<Integer, Runnable> allowablePermissionRunnables = new HashMap<>();
    private Map<Integer, Runnable> disallowablePermissionRunnables = new HashMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(getLayoutResID());
        View rootView = findViewById(android.R.id.content);//获取activity的根view
        Utils.setButtonOnClickListener(rootView,this);
        initView();
        initListener();
        initData();
    }

    /**
     * 查找view,这个方法可以省去我们的强转操作
     */
    public <T> T findView(int id){
        T view=(T)findViewById(id);
        return view;
    }

    //在屏幕中间显示一个Toast
    public void showToast(String text){
        Utils.showToast(text,this);
    }

    @Override
    public void onClick(View v) {
//        if(v.getId()== android.R.id.home){//处理共同操作
//            Log.e("v.getId()","wwww");
//            finish();
//        }else{
//            //如果单击的不是返回按钮,则还是由子类去做处理
//            onclick(v,v.getId());
//        }
        onclick(v,v.getId());
    }

    /**
     * 请求权限
     * @param id 请求授权的id 唯一标识即可
     * @param permission 请求的权限
     * @param allowableRunnable 同意授权后的操作
     * @param disallowableRunnable 禁止权限后的操作
     */
    public void requestPermission(int id, String permission, Runnable allowableRunnable, Runnable disallowableRunnable) {
        if (allowableRunnable == null||disallowableRunnable==null) {
            return;
        }
        allowablePermissionRunnables.put(id, allowableRunnable);
        disallowablePermissionRunnables.put(id, disallowableRunnable);

        //版本判断
        if (Build.VERSION.SDK_INT >= 23) {
            //检查是否拥有权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
//            Log.e("wws",checkCallPhonePermission+"");
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //弹出对话框接收权限
//                Log.e("www",permission+"请求");
                ActivityCompat.requestPermissions(BaseActivity.this, new String[]{permission}, id);
            } else {
                allowableRunnable.run();
            }
        } else {
            allowableRunnable.run();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Runnable allowRun = allowablePermissionRunnables.get(requestCode);
            allowRun.run();
        } else {
            Runnable disallowRun = disallowablePermissionRunnables.get(requestCode);
            disallowRun.run();
        }
    }

}
