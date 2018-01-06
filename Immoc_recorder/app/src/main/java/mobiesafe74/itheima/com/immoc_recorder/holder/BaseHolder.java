package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.content.Context;
import android.util.Log;
import android.view.View;

import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;

/**
 * Created by Administrator on 2017/5/1.
 */

public abstract class BaseHolder<T> {
    private View mRootView;
    private T data;
    private int mTag;
    private Context mContext;

    public void setRootView(View rootView) {
        mRootView = rootView;
    }

    public int getTag() {
        return mTag;
    }

    public void setTag(int tag) {
        mTag = tag;
    }

    public View getRootView() {
        return mRootView;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context){
        Log.e("context",context+"");
        mContext=context;
    }

    public T getData() {
        return data;
    }

    public void setData(T data){
        this.data=data;
        refreshView(data);
    }

    public BaseHolder(){
        mRootView=initView();
    }

    public abstract void initData();

    public abstract View initView();

    protected abstract void refreshView(T data);
}
