package com.yayangyang.findlastvisibleitempositiontestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;

import java.util.ArrayList;
import java.util.List;

//RecyclerView的scrollToPosition方法直接到达指定item没有滚动效果,
// 滚动监听只会响应一次(此时dx==0,dy==0,findFirstVisibleItemPosition也是到达的item的position)

//RecyclerView的smoothScrollToPosition方法有滚动效果,滚动监听会多次响应
public class MainActivity extends AppCompatActivity implements BaseQuickAdapter.OnItemClickListener{

    private List<String> mStringList=new ArrayList<>();
    private RecyclerView mRecyclerView;

    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        myAdapter = new MyAdapter(R.layout.item, mStringList);
        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(myAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                Log.e("onScrolled-x",dx+"");
                Log.e("onScrolled-y",dy+"");

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//                Log.e("FirstVisiblePosition",layoutManager.findFirstVisibleItemPosition()+"");
            }
        });

    }

    private void initData() {
        for(int i=0;i<300;i++){
            mStringList.add("ww"+i);
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Log.e("ww","ee");
        mRecyclerView.scrollToPosition(100);
//        mRecyclerView.smoothScrollToPosition(10);
    }

}
