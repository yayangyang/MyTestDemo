package com.yayangyang.garbagecollectiontestdemo;

import android.util.Log;

/**
 * Created by Administrator on 2017/12/5.
 */

public class Test {
    public MainActivity mActivity;

    public MainActivity getActivity() {
        return mActivity;
    }

    public void setActivity(MainActivity activity) {
        mActivity = activity;
    }

    public void clear(){
        if(mActivity!=null){
            mActivity.mTest=null;
        }

//        System.runFinalization();
//        System.gc();

        for(int i=0;i<100000;i++){
            if(i%10000==0){
                try {
                    Thread.sleep(100);
                    Log.e("主线程","睡眠0.1秒"+i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        System.gc();
//        System.runFinalization();
//        System.gc();
    }

    public void finalize(){
        Log.e("垃圾回收啦！！！","wwwwwwwww");
    }
}
