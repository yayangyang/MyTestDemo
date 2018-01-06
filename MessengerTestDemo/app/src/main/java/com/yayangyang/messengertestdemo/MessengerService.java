package com.yayangyang.messengertestdemo;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Administrator on 2018/1/1.
 */

public class MessengerService extends Service {

    private static final String TAG="MessengerService";

    private static class MessengerHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG,"handleMessage");
            if(msg.what==MyConstant.MSG_FROM_CLIENT){
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                Log.e(TAG,"receive msg from Client:"+msg.getData().getString("msg"));
                Messenger client=msg.replyTo;
                Message replyMessage=Message.obtain(null,MyConstant.MSG_FROM_SERVICE);
                Bundle bundle = new Bundle();
                bundle.putString("reply","已收到消息,稍后回复");
                replyMessage.setData(bundle);
                try {
                    Log.e(TAG,"ww11");
                    client.send(replyMessage);
                    Log.e(TAG,"ww22");
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private final Messenger mMessenger=new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG,"onBind");
        return mMessenger.getBinder();
    }
}
