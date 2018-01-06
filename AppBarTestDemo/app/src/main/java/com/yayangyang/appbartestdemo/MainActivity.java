package com.yayangyang.appbartestdemo;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //加上这句且在CollapsingToolbarLayout设置
        //app:statusBarScrim="@android:color/transparent"状态栏透明
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
//        toolbar.setSubtitle("这里是子标题");
        toolbar.setNavigationIcon(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);

//        ((CollapsingToolbarLayout)findViewById(R.id.collapsingToolbarLayout))
//                .setContentScrim(getResources().getDrawable(R.mipmap.ic_launcher));

        String str="";
        for(int i=0;i<1000;i++){
            str+="wwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwww";
        }
        TextView tv = findViewById(R.id.tv);
        tv.setText(str);
    }
}
