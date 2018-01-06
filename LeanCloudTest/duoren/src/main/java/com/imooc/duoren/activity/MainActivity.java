package com.imooc.duoren.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.imooc.duoren.R;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void tom(View view){
        sendMessageToJerryFromTom();
    }

    public void bob(View view){
        loginAsBob();
    }

    public void harry(View view){
        loginAsHarry();
    }

    public void william(View view){
        loginAsWilliam();
    }

    public void sendMessageToJerryFromTom() {
        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
        AVIMClient tom = AVIMClient.getInstance("Tom");
        // 与服务器连接
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建与 Jerry，Bob,Harry,William 之间的对话
                    client.createConversation(Arrays.asList("Jerry","Bob","Harry","William"), "Tom & Jerry & friedns", null,
                    new AVIMConversationCreatedCallback() {
                        @Override
                        public void done(AVIMConversation conversation, AVIMException e) {
                            if (e == null) {
                                AVIMTextMessage msg = new AVIMTextMessage();
                                msg.setText("你们在哪儿？");
                                // 发送消息
                                conversation.sendMessage(msg, new AVIMConversationCallback() {
                                    @Override
                                    public void done(AVIMException e) {
                                        if (e == null) {
                                            Log.e("Tom & Jerry", "发送成功！");
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });
    }

    public void loginAsBob() {
        AVIMClient bob = AVIMClient.getInstance("Bob");
        //Bob登录
        bob.open(new AVIMClientCallback() {
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    //登录成功
                    Toast.makeText(getApplicationContext(),"Bob登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginAsHarry() {
        AVIMClient bob = AVIMClient.getInstance("Harry");
        //Bob登录
        bob.open(new AVIMClientCallback() {
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    //登录成功
                    Toast.makeText(getApplicationContext(),"Harry登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void loginAsWilliam() {
        AVIMClient bob = AVIMClient.getInstance("William");
        //Bob登录
        bob.open(new AVIMClientCallback() {
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    //登录成功
                    Toast.makeText(getApplicationContext(),"William登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
