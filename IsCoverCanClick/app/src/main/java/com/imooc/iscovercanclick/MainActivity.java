package com.imooc.iscovercanclick;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

//按钮被盖住即不可点击,会被其上的按钮拦截
//设置enabled属性为false的按钮默认在没设置这个或设置为true的按钮之下且不可点击,
//后添加的enabled=false在先添加enabled=false之上
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void dianji1(View view){
        Log.e("dianji1","dianji1");
    }

    public void dianji2(View view){
        Log.e("dianji2","dianji2");
    }

}
