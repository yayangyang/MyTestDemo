package com.imooc.setvisibletestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mMyAdapter;
    private ArrayList mArrayList=new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        configView();
    }

    private void initData() {
        for(int i=0;i<10;i++){
            mArrayList.add("test");
        }
    }

    private void configView() {
        mRecyclerView=findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mRecyclerView.setVisibility(View.VISIBLE);
        mMyAdapter=new MyAdapter(R.layout.activity_item,mArrayList);
        mRecyclerView.setAdapter(mMyAdapter);
    }

    public void dianji(View view){
        Log.e("ww","oo");
        //如果改变会重绘view
        if(mRecyclerView.getVisibility()==View.GONE){
            mRecyclerView.setVisibility(View.VISIBLE);
        }else{
            mRecyclerView.setVisibility(View.GONE);
        }
    }

}
