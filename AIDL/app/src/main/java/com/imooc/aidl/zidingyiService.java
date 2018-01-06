package com.imooc.aidl;

import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/6.
 */

public class zidingyiService extends Service {

    private ArrayList<Person> persons;
    private boolean isFirst=true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("ww","onBind");
        persons=new ArrayList<Person>();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        boolean b = Looper.getMainLooper() == Looper.myLooper();
        Log.e("是否在主线程:",b+"");
        Log.e("mIBinder",mIBinder+"");
        return mIBinder;
    }

    private IBinder mIBinder=new zidingyi.Stub(){
        @Override
        public List<Person> add(Person person) throws RemoteException {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
//            if(isFirst){
//                isFirst=false;
//                int a=5/0;//由别的进程调用导致异常service不会crash
//            }
//            boolean b = Looper.getMainLooper() == Looper.myLooper();
//            Log.e("是否在主线程:",b+"");
            for(int i=0;i<1000;i++){
                Log.e("ww",i+"");
            }
            persons.add(person);
            return persons;
        }
    };

    @Override
    public void onDestroy() {
        Log.e("zidingyiService","onDestroy");
        super.onDestroy();
    }
}
