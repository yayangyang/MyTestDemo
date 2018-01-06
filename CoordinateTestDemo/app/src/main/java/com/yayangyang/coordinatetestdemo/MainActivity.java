package com.yayangyang.coordinatetestdemo;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

//使用scroller移动ViewGroup里的内容没有改变如view的Left,TranslationX的值
public class MainActivity extends AppCompatActivity {

    private String TAG="MainActivity";

    private List<String> mStringList=new ArrayList<>();
    private MyAdapter myAdapter;
    private RecyclerView recyclerView;

    private boolean b=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);;

        initData();
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter(R.layout.item, mStringList);
        recyclerView.setAdapter(myAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int top = recyclerView.getChildAt(0).getTop();
                int childCount = recyclerView.getChildCount();
                Log.e("getScrollY:",recyclerView.getScrollY()+"");
                Log.e("getY:",recyclerView.getChildAt(0).getY()+"");
                Log.e("FirstVisibleItemTop:",top+"");
                Log.e("FirstVisibleItem-TY:",recyclerView.getChildAt(0).getTranslationY()+"");
                Log.e("childCount:",childCount+"");
            }
        });

        findViewById(R.id.fr).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_MOVE){
                    return false;
                }else if(event.getAction()==MotionEvent.ACTION_UP){
//                    v.setX(v.getX()-30);
//                    v.scrollTo(v.getScrollX()-50,0);
//                    Log.e(TAG+"-fr-getScrollX:",v.getScrollX()+"");
//                    v=v.findViewById(R.id.tv);
//                    Log.e(TAG+"-tv-getLeft:",v.getLeft()+"");
//                    Log.e(TAG+"-tv-getTranslationX:",v.getTranslationX()+"");

                    //两个放在一起只会执行一次
//                    v.invalidate();
//                    v.invalidate();

                    //两个放在一起只会执行一次
                    v.requestLayout();
                    v.requestLayout();
                }
                return true;
            }
        });

        findViewById(R.id.tv).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction()==MotionEvent.ACTION_UP){
                    Log.e("tv","up");
                    v.setX(v.getX()-50);
                    Log.e(TAG+"-fr-getScrollX:",v.getScrollX()+"");
                    Log.e(TAG+"-tv-getLeft:",v.getLeft()+"");
                    Log.e(TAG+"-tv-getTranslationX:",v.getTranslationX()+"");
                }
                return true;
            }
        });

//        findViewById(R.id.recyclerView).setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if(event.getAction()==MotionEvent.ACTION_UP){
//                    v.setX(v.getX()-30);
//                    Log.e(TAG+"-rv-getLeft:",v.getLeft()+"");
//                    Log.e(TAG+"-rv-getTranslationX:",v.getTranslationX()+"");
//                }
//                return true;
//            }
//        });

//        ObjectAnimator.ofFloat(findViewById(R.id.tv),
//                "translationX",0,-500).setDuration(5000).start();
    }

    private void initData() {
        for(int i=0;i<100;i++){
            mStringList.add("ww"+i);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e(TAG,"onTouchEvent");
        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
