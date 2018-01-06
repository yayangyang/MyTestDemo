package com.imooc.leancloudtest.activity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage;
import com.imooc.leancloudtest.R;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.avos.avoscloud.Messages.CommandType.conv;
import static com.avos.avoscloud.im.v2.AVIMMessageManager.registerMessageHandler;

public class MainActivity extends AppCompatActivity {

    private AVIMTypedMessageHandler handlerImage;
    private AVIMTypedMessageHandler handlerAudio;
    private AVIMTypedMessageHandler handlerVideo;
    private AVIMTypedMessageHandler handlerLocation;
    public static int wz=0;
    public static int image1=1;
    public static int image2=2;
    public static int audio1=3;
    public static int audio2=4;
    public static int video1=5;
    public static int video2=6;
    public static int location=7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });
    }

    public void tom(View view){
        sendMessageToJerryFromTom("",wz);
    }

    public void toImage1(View view){
        Log.e("ww", Environment.getExternalStorageDirectory()+"");
        String filePath=Environment.getExternalStorageDirectory()+"/Pictures/a.jpg";
        sendMessageToJerryFromTom(filePath,image1);
    }

    public void toImage2(View view){
        String filePath="http://pic2.zhimg.com/6c10e6053c739ed0ce676a0aff15cf1c.gif";
        sendMessageToJerryFromTom(filePath,image2);
    }

    public void toAudio1(View view){
        String filePath=Environment.getExternalStorageDirectory()+"/Music/sound01.mp3";
        sendMessageToJerryFromTom(filePath,audio1);
    }

    public void toAudio2(View view){
        String filePath="http://ac-lhzo7z96.clouddn.com/1427444393952";
        sendMessageToJerryFromTom(filePath,audio2);
    }

    public void toVideo1(View view){
        Log.e("toVideo1","toVideo1");
        String filePath=Environment.getExternalStorageDirectory()+"/Movies/video.mp4";
        sendMessageToJerryFromTom(filePath,video1);
    }

    public void toVideo2(View view){
        String filePath="http://ac-lhzo7z96.clouddn.com/1427267336319";
        sendMessageToJerryFromTom(filePath,video2);
    }

    public void toLocation(View view){
        String point="138.12454,52.56461";
        sendMessageToJerryFromTom(point,location);
    }

    public void sendWz(AVIMClient client) {
        Toast.makeText(getApplicationContext(),"tom登录成功",Toast.LENGTH_SHORT).show();
        // 创建与Jerry之间的对话
        client.createConversation(Arrays.asList("Jerry"), "Tom & Jerry", null,
            new AVIMConversationCreatedCallback() {
                @Override
                public void done(AVIMConversation conversation, AVIMException e) {
                    if (e == null) {
                        AVIMTextMessage msg = new AVIMTextMessage();
                        msg.setText("耗子，起床！");
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

    public void sendImage1(final AVIMClient client, final String filePath){
        //登录成功
        // 创建对话，默认创建者是在包含在成员列表中的
        client.createConversation(Arrays.asList("Jerry"),"ss",null, new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation conv, AVIMException e) {
                Log.e("登录成功","image1");
                if (e == null) {
                    Log.e("登录成功","登录成功");
                    AVIMImageMessage picture = null;
                    try {
                        picture = new AVIMImageMessage(filePath);
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    picture.setText("发自我的小米");
                    Map < String, Object > attributes = new HashMap < String, Object > ();
                    attributes.put("location", "旧金山");
                    picture.setAttrs(attributes);
                    conv.sendMessage(picture, new AVIMConversationCallback() {
                        @Override
                        public void done(AVIMException e) {
//                                        if(e==null){
//                                            Log.e("e","为空");
//                                        }else if(e==null){
//                                            Log.e("e","不为空");
//                                        }
                            if (e == null) {
                                //发送成功！
                                Log.e("image1","发送成功");
                            }
                        }
                    });
                }
            }
        });
    }

    public void sendImage2(final AVIMClient client, final String filePath){
        client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
            new AVIMConversationCreatedCallback() {
                @Override
                public void done(AVIMConversation conv, AVIMException e) {
                    if (e == null) {
                        AVFile file =new AVFile("萌妹子",filePath, null);
                        AVIMImageMessage m = new AVIMImageMessage(file);
                        m.setText("萌妹子一枚");
                        // 创建一条图片消息
                        conv.sendMessage(m, new AVIMConversationCallback() {
                            @Override
                            public void done(AVIMException e) {
                                if (e == null) {
                                    // 发送成功
                                    Log.e("image2","发送成功");
                                }
                            }
                        });
                    }
                }
            });
    }

    public void sendAudio1(final AVIMClient client, final String filePath){
        // 创建名为“猫和老鼠”的对话
        client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
                            AVFile file = null;
                            try {
                                file = AVFile.withAbsoluteLocalPath("sound01.mp3",filePath);
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            AVIMAudioMessage m = new AVIMAudioMessage(file);
                            m.setText("听听人类的神曲~");
                            // 创建一条音频消息
                            conv.sendMessage(m, new AVIMConversationCallback() {
                                @Override
                                public void done(AVIMException e) {
                                    if (e == null) {
                                        // 发送成功
                                        Log.e("music1","发送成功");
                                    }
                                }
                            });
                        }
                    }
                });
    }

    public void sendAudio2(final AVIMClient client, final String filePath){
        // 创建名为「猫和老鼠」的对话
        client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
                            AVFile file = new AVFile("music", filePath, null);
                            AVIMAudioMessage m = new AVIMAudioMessage(file);
                            // 创建一条音频消息
                            conv.sendMessage(m, new AVIMConversationCallback() {
                                @Override
                                public void done(AVIMException e) {
                                    if (e == null) {
                                        // 发送成功
                                        Log.e("music2","发送成功");
                                    }
                                }
                            });
                        }
                    }
                });
    }

    public void sendVideo1(final AVIMClient client, final String filePath){
        // 创建名为“猫和老鼠”的对话
        client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
            new AVIMConversationCreatedCallback() {
                @Override
                public void done(AVIMConversation conv, AVIMException e) {
                    Log.e("www","搜索1");
                    if (e == null) {
                        Log.e("www","搜索2");
                        AVFile file = null;
                        try {
                            file = AVFile.withAbsoluteLocalPath("bbc_奶酪.mp4", filePath);
                            Log.e("www","搜索3");
                        } catch (FileNotFoundException e1) {
                            Log.e("www","搜索4");
                            e1.printStackTrace();
                        }
                        Log.e("www","搜索6");
                        AVIMVideoMessage m = new AVIMVideoMessage(file);
                        Log.e("www","搜索7"+filePath);
                        // 创建一条视频消息
                        conv.sendMessage(m, new AVIMConversationCallback() {
                            @Override
                            public void done(AVIMException e) {
                                Log.e("www","搜索5");
                                if (e == null) {
                                    // 发送成功
                                    Log.e("video1","发送成功");
                                }
                            }
                        });
                    }
                }
            });
    }

    public void sendVideo2(final AVIMClient client, final String filePath){
        // 创建名为「猫和老鼠」的对话
        client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
                            AVFile file = new AVFile("video",filePath, null);
                            AVIMVideoMessage m = new AVIMVideoMessage(file);
                            // 创建一条视频消息
                            conv.sendMessage(m, new AVIMConversationCallback() {
                                @Override
                                public void done(AVIMException e) {
                                    if (e == null) {
                                        // 发送成功
                                        Log.e("video2","发送成功");
                                    }
                                }
                            });
                        }
                    }
                });
    }

    public void sendLocation(final AVIMClient client, final String filePath){
                            // 创建名为「猫和老鼠」的对话
                            client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
                                    new AVIMConversationCreatedCallback() {
                                        @Override
                                        public void done(AVIMConversation conv, AVIMException e) {
                                            if (e == null) {
                            final AVIMLocationMessage locationMessage=new AVIMLocationMessage();
                            String[] strings = filePath.split(",");
                            locationMessage.setLocation(new AVGeoPoint(Float.parseFloat(strings[0]),
                                    Float.parseFloat(strings[1])));
                            conv.sendMessage(locationMessage, new AVIMConversationCallback() {
                                @Override
                                public void done(AVIMException e) {
                                    if (null != e) {
                                        e.printStackTrace();
                                    } else {
                                        // 发送成功
                                        Log.e("location","发送成功");
                                    }
                                }
                            });
                        }
                    }
                });
    }

    public void sendMessageToJerryFromTom(final String filePath,final int type) {
        // Tom 用自己的名字作为clientId，获取AVIMClient对象实例
        AVIMClient tom = AVIMClient.getInstance("Tom");
        // 与服务器连接
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    if(type==wz){
                        sendWz(client);
                    }else if(type==image1){
                        sendImage1(client,filePath);
                    }else if(type==image2){
                        sendImage2(client,filePath);
                    }else if(type==audio1){
                        sendAudio1(client,filePath);
                    }else if(type==audio2){
                        sendAudio2(client,filePath);
                    }else if(type==video1){
                        sendVideo1(client,filePath);
                    }else if(type==video2){
                        sendVideo2(client,filePath);
                    }else if(type==location){
                        sendLocation(client,filePath);
                    }
                }
            }
        });
    }

    public void jerry(View view){
        loginAsJerry();
    }

    public void loginAsJerry(){
        //Jerry登录
        AVIMClient jerry = AVIMClient.getInstance("Jerry");
        jerry.open(new AVIMClientCallback(){
            @Override
            public void done(AVIMClient client,AVIMException e){
                if(e==null){
                    //登录成功后的逻辑
                    Toast.makeText(getApplicationContext(),"jerry登录成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        Log.e("ww","ww");
        //注册消息处理逻辑
        handlerImage=new AVIMTypedMessageHandler<AVIMImageMessage>() {
            @Override
            public void onMessage(AVIMImageMessage msg, AVIMConversation conv, AVIMClient client) {
                //只处理 Tom 这个客户端的消息
//                Log.e("ee",client.getClientId()+"eee");
                if ("Jerry".equals(client.getClientId()) ) {
                    String fromClientId = msg.getFrom();
                    String messageId = msg.getMessageId();
                    String url = msg.getFileUrl();
                    Log.e("url",url+"");
                    Map<String, Object> metaData = msg.getFileMetaData();
                    if (metaData.containsKey("size")) {
                        int size = (Integer) metaData.get("size");
                    }
                    if (metaData.containsKey("width")) {
                        int width = (Integer) metaData.get("width");
                    }
                    if (metaData.containsKey("height")) {
                        int height = (Integer) metaData.get("height");
                    }
                    if (metaData.containsKey("format")) {
                        String format = (String) metaData.get("format");
                    }
                }
            }
        };

        handlerAudio=new AVIMTypedMessageHandler<AVIMAudioMessage>() {
            @Override
            public void onMessage(AVIMAudioMessage msg, AVIMConversation conv, AVIMClient client) {
                //只处理 Jerry 这个客户端的消息
                //并且来自 conversationId 为 55117292e4b065f7ee9edd29 的conversation 的消息
                if ("Jerry".equals(client.getClientId())) {
                    String fromClientId = msg.getFrom();
                    String messageId = msg.getMessageId();
                    String url = msg.getFileUrl();
                    Log.e("music1",url);
                    Map<String, Object> metaData = msg.getFileMetaData();
                    if (metaData.containsKey("size")) {
                        int size = (Integer) metaData.get("size");
                    }
                    if (metaData.containsKey("format")) {
                        String format = (String) metaData.get("format");
                    }
                }
            }
        };

        handlerVideo=new AVIMTypedMessageHandler<AVIMVideoMessage>() {
            @Override
            public void onMessage(AVIMVideoMessage msg, AVIMConversation conv, AVIMClient client) {
                //只处理 Jerry 这个客户端的消息
                //并且来自 conversationId 为 55117292e4b065f7ee9edd29 的conversation 的消息
                if ("Jerry".equals(client.getClientId())) {
                    String fromClientId = msg.getFrom();
                    String messageId = msg.getMessageId();
                    String url = msg.getFileUrl();
                    Log.e("video1",url);
                    Map<String, Object> metaData = msg.getFileMetaData();
                    if (metaData.containsKey("size")) {
                        int size = (Integer) metaData.get("size");
                    }
                    if (metaData.containsKey("format")) {
                        String format = (String) metaData.get("format");
                    }
                }
            }
        };

        handlerLocation=new AVIMTypedMessageHandler<AVIMLocationMessage>() {
            @Override
            public void onMessage(AVIMLocationMessage msg, AVIMConversation conv, AVIMClient client) {
                Log.e("ww","wwww");
                //只处理 Tom 这个客户端的消息
//                Log.e("ee",client.getClientId()+"eee");
                if ("Jerry".equals(client.getClientId()) ) {
                    String fromClientId = msg.getFrom();
                    String messageId = msg.getMessageId();
                    AVGeoPoint point = msg.getLocation();
                    Log.e("经度",point.getLongitude()+"");
                    Log.e("纬度",point.getLatitude()+"");
                }
            }
        };

        registerMessageHandler(AVIMImageMessage.class, handlerImage);
        registerMessageHandler(AVIMAudioMessage.class, handlerAudio);
        registerMessageHandler(AVIMVideoMessage.class, handlerVideo);
        registerMessageHandler(AVIMLocationMessage.class,handlerLocation);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVIMMessageManager.unregisterMessageHandler(AVIMImageMessage.class,handlerImage);
        AVIMMessageManager.unregisterMessageHandler(AVIMAudioMessage.class, handlerAudio);
        AVIMMessageManager.unregisterMessageHandler(AVIMVideoMessage.class, handlerVideo);
        AVIMMessageManager.unregisterMessageHandler(AVIMLocationMessage.class,handlerLocation);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
