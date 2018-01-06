package com.imooc.imooc_imageloader.Utils;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.LruCache;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/5/26.
 */

public class ImageLoader {
    private static ImageLoader mInstance;

    private LruCache<String,Bitmap> mLruCache;//图片缓存的核心对象
    private ExecutorService mThreadPool;//线程池
    private static final int DEAFULT_THREAD_COUNT=1;
    private Type mType=Type.LIFO;//队列的调度方式
    public enum Type{
        FIFI,LIFO;
    }
    private LinkedList<Runnable> mTaskQueue;//任务队列
    private Thread mPoolThread;//后台轮循线程
    private Handler mPoolThreadHandler;
    private Handler mUIHandle;//UI线程中的Handler

    private ImageLoader(int threadCount,Type type){

    }

    //初始化
    private void init(int threadCount,Type type){
        //后台轮循线程
        mPoolThread=new Thread(){
            @Override
            public void run() {
                super.run();
                Looper.prepare();
                mPoolThreadHandler=new Handler(){
                    @Override
                    public void handleMessage(Message msg) {
                        super.handleMessage(msg);
                        //线程池去除一个任务进行执行
                    }
                };
                Looper.loop();
            }
        };
        mPoolThread.start();
        //获取我们应用的最大可用内存
        int maxMemory= (int) Runtime.getRuntime().maxMemory();
        int cacheMemory=maxMemory/8;

        mLruCache=new LruCache<String, Bitmap>(cacheMemory){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getRowBytes()*value.getHeight();
            }
        };
        mThreadPool= Executors.newFixedThreadPool(threadCount);//创建线程池
        mTaskQueue=new LinkedList<Runnable>();
        mType=type;
    }

    public static ImageLoader getInstance(){
        if(mInstance==null){
            synchronized (ImageLoader.class){
                if(mInstance==null){
                    mInstance=new ImageLoader(DEAFULT_THREAD_COUNT,Type.LIFO);
                }
            }
        }
        return mInstance;
    }

}
