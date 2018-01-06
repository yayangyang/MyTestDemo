package mobiesafe74.itheima.com.immoc_recorder.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity;

/**
 * Created by Administrator on 2017/4/30.
 */

public class MyListView extends ListView {

    private ChatActivity mChatActivity;
    public boolean canScroll=false;
    public static boolean isEmpty=false;//聊天记录是否已到顶(已完)
    public static boolean isScrolling=false;

    public MyListView(Context context) {
        super(context);
        gundong();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        gundong();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gundong();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        gundong();
    }

    public void gundong(){
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(totalItemCount>visibleItemCount){
//                    Log.e("firstVisibleItem",firstVisibleItem+"");
//                    Log.e("visibleItemCount",visibleItemCount+"");
//                    Log.e("totalItemCount",totalItemCount+"");
//                    Log.e("isEmpty",isEmpty+"");
//                    Log.e("canScroll",canScroll+"");
//                    Log.e("isScrolling",isScrolling+"");
                    if(firstVisibleItem==0){
                        if(!isEmpty){//为空则不加载,优化性能
                            if(canScroll){//刚开始onscroll会自动调用,改为按下按钮时canScroll才为true
//                                Log.e("搜索","发反反复复付");
                                if(!isScrolling){//onScroll满足条件时仍频繁调用,给其设置约束
                                    isScrolling=true;
                                    sendUtil.getMessage(10);
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if(ev.getAction()==MotionEvent.ACTION_DOWN){
            canScroll=true;
            if(mChatActivity!=null){
                if(mChatActivity.isOpen()){
                    mChatActivity.reset();
                    mChatActivity.jianpan();
                }
            }
        }
        return super.onTouchEvent(ev);
    }

    public void setChatActivity(ChatActivity chatActivity){
        this.mChatActivity = chatActivity;
    }
}
