package com.imooc.duoren.global;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageHandler;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MyLeanCloudApp extends Application {
    public static class CustomMessageHandler extends AVIMMessageHandler {
        //接收到消息后的处理逻辑
        @Override
        public void onMessage(AVIMMessage message, AVIMConversation conversation, AVIMClient client){
            if(message instanceof AVIMTextMessage){
                Log.e("Tom & Jerry请求",((AVIMTextMessage)message).getText());
            }
        }

        public void onMessageReceipt(AVIMMessage message, AVIMConversation conversation, AVIMClient client){

        }
    }
    @Override
    public void onCreate() {
        super.onCreate();

        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this,"2UaP4eVNMHMm1bKcHvTWVRde-gzGzoHsz","pLIYNGv5PhKQbifzClzH84O5");
        //注册默认的消息处理逻辑
        AVIMMessageManager.registerDefaultMessageHandler(new CustomMessageHandler());
        // 放在 SDK 初始化语句 AVOSCloud.initialize() 后面，只需要调用一次即可
        AVOSCloud.setDebugLogEnabled(true);//开启调试日志
//        jerryReceiveMsgFromTom();
    }
}
