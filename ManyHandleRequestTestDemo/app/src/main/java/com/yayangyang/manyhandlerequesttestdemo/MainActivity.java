package com.yayangyang.manyhandlerequesttestdemo;

import android.os.Handler;
import android.os.Message;
import android.os.MessageQueue;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import java.util.ArrayList;
import java.util.List;

//触摸事件应该是由其他线程发送到主线程执行的
//触摸事件发送到主线程之前可能被选择性丢弃
//若把下面放到消息队列里的代码看成重绘,那么有可能出现几个事件一起绘制的情况,
//太多事件一起绘制会很突兀,但是这种情况很少,一般是末尾出现两三个一起绘制(即几个触摸事件挨着放在消息队列里)
//android动态控制move时间之间的间隔(根据延迟适当调整),
// 不让多个move在消息队列连在一起导致丢失绘制信息造成的跳动(可能会有意外情况即两三个连在一起),
//而点击不同地方可能没有控制时间间隔
//可能是根据间隔时间和耗能情况给出move事件的
public class MainActivity extends AppCompatActivity {

    private int a=0;
    private List<String> mStringList=new ArrayList<>();

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            Log.e("handleMessage","handleMessage");
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        MyAdapter myAdapter = new MyAdapter(R.layout.item, mStringList);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(myAdapter);
        recyclerView.setClickable(true);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                a++;
                Log.e("onTouch",a+"");
                for(int i=0;i<10000;i++){//循环语句基本不花费时间(1亿个都能看到滚动效果)
//                    Log.d("i",i+"");//许多个log会很耗性能(1千个能看到滚动效果)
//                    a=a+1;//赋值语句基本不花费时间(1千万个都能看到滚动效果)
//                    TextUtils.isEmpty(a+"");//10万个能看到滚动效果)
//                    if(a==0){}//判断语句基本不花费时间(1千万个都能看到滚动效果)
                }
                mHandler.sendEmptyMessage(0);
//                Log.e("ee","eeee");
                return false;
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                Log.e("dx",dx+"");
                Log.e("dy",dy+"");
            }
        });
    }

    private void initData() {
        for(int i=0;i<300;i++){
            mStringList.add("ww"+i);
        }
    }

}
