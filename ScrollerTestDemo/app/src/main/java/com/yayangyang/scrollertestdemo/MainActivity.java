package com.yayangyang.scrollertestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    ScrollView mScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mScrollView=findViewById(R.id.root);
        //API23才可使用的方法(不知有没兼容包)
        mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                Log.e("scrollY",scrollY+"");
                Log.e("tv03-getTop",findViewById(R.id.tv03).getTop()+"");
            }
        });
    }

    public void change(View view){
        Button bt = (Button) view;
        bt.setTextSize(60);
    }

}
