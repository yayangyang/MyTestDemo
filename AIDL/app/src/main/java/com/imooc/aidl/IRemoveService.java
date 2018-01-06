package com.imooc.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/5.
 */

public class IRemoveService extends Service{

    /**
     * 当客户端绑定到该服务的时候会执行
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("ww","onBind");
        return mIBinder;
    }

    private IBinder mIBinder=new IImoocAidl.Stub(){

        @Override
        public int add(int num1, int num2) throws RemoteException {

            Log.d("TAG","收到了远程的请求,输入的参数是"+num1+"和"+num2);
            return num1+num2;
        }
    };

//    private IBinder mIBinder2=new IMyAidlInterface.Stub(){
//
//        @Override
//        public List<String> basicTypes(byte aByte, int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, char aChar, String aString, List<String> aList) throws RemoteException {
//
//            return null;
//        }
//    };

}
