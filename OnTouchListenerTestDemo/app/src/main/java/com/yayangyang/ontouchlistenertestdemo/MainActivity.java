package com.yayangyang.ontouchlistenertestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.bt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick","onClick");
            }
        });

        findViewById(R.id.bt).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    Log.e("onTouch","ACTION_DOWN");
                }else if(event.getAction()==MotionEvent.ACTION_MOVE){
                    Log.e("onTouch","ACTION_MOVE");
                }else if(event.getAction()==MotionEvent.ACTION_UP){
                    Log.e("onTouch","ACTION_UP");
                }
                return false;
            }
        });
    }
}
