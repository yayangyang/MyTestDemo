package com.yayangyang.quotetestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

//调用一个对象的方法过程中该对象不会被GC
public class MainActivity extends AppCompatActivity {

    private final static String TAG="MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v=null;
                RelativeLayout relativeLayout = findViewById(R.id.root);
                relativeLayout.removeAllViews();
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(int i=0;i<100;i++){
                    Log.e(TAG,"ww"+i);
                }
            }
        });
    }
}
