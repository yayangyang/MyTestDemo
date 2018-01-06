package com.imooc.chumodemo;

import android.os.Looper;
import android.os.Message;
import android.os.MessageQueue;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private ConstraintLayout mConstraintLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConstraintLayout= (ConstraintLayout) findViewById(R.id.root);
        mConstraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a="ww";
                String b=a;
                b="ee";
                Log.e("a",""+a);
                Log.e("b",""+b);
                Log.e("isMainThread",""+isMainThread());
            }
        });
    }

    public boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("isMainThreadeeeeeee",""+isMainThread());
        return super.dispatchTouchEvent(ev);
    }
}
