package com.yayangyang.keyboardtestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

//要使键盘弹起关闭使我们的根布局重新布局可以给Activity添加
//属性android:windowSoftInputMode="adjustResize",或布局中右scrollView或RecyclerView其中一个
public class MainActivity extends AppCompatActivity{

    private List<String> mStringList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
//        initKeyBoard();
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        MyAdapter myAdapter = new MyAdapter(R.layout.item, mStringList);
        recyclerView.setAdapter(myAdapter);

//        myAdapter.setEmptyView(View.inflate(this,R.layout.view_empty,null));
//        findViewById(android.R.id.content).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mStringList.add("123");
//                myAdapter.notifyItemInserted(myAdapter.getData().size()+myAdapter.getHeaderLayoutCount());
//            }
//        });
    }

    private void initKeyBoard() {
        int screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        int keyHeight = screenHeight / 3;

        findViewById(android.R.id.content).addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.e("keyHeight:",keyHeight+"");
                Log.e("oldBottom:",oldBottom+"");
                Log.e("bottom:",bottom+"");
                Log.e("oldBottom - bottom:",(oldBottom - bottom)+"");
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    Log.e("键盘弹起","ww");
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    Log.e("键盘落下","ww");
                }
            }
        });
    }

    private void initData() {
        for(int i=0;i<30;i++){
            mStringList.add("ww"+i);
        }
    }
}
