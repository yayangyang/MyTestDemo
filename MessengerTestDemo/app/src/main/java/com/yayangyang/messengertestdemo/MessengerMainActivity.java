package com.yayangyang.messengertestdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MessengerMainActivity extends AppCompatActivity {

    private static final String TAG="MessengerMainActivity";

    private Messenger mService;

    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            Log.e(TAG,"handleMessage");
            Log.e("msg.what",msg.what+"");
            if(msg.what==MyConstant.MSG_FROM_SERVICE){
                Log.e(TAG,"receive msg from Service:"+msg.getData().getString("reply"));
            }
        }
    }

    private final Messenger mGetReplyMessenger=new Messenger(new MessengerHandler());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, MessengerService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);
    }

    public ServiceConnection mConnection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("连接","ww");
            mService=new Messenger(service);
            Message msg=Message.obtain(null,MyConstant.MSG_FROM_CLIENT);
            Bundle data=new Bundle();
            data.putString("msg","hello,this is client");
            msg.setData(data);
            msg.replyTo=mGetReplyMessenger;
            try {
                Log.e(TAG,"ww11");
                mService.send(msg);
                Log.e(TAG,"ww22");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("断开","ww");
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(mConnection);
        super.onDestroy();
    }
}
