package com.imooc.leancloudtest.utils;

import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static com.imooc.leancloudtest.activity.MainActivity.audio1;
import static com.imooc.leancloudtest.activity.MainActivity.audio2;
import static com.imooc.leancloudtest.activity.MainActivity.image1;
import static com.imooc.leancloudtest.activity.MainActivity.image2;
import static com.imooc.leancloudtest.activity.MainActivity.location;
import static com.imooc.leancloudtest.activity.MainActivity.video1;
import static com.imooc.leancloudtest.activity.MainActivity.video2;
import static com.imooc.leancloudtest.activity.MainActivity.wz;

/**
 * Created by Administrator on 2017/5/11.
 */

public class sendUtil {
    public static void sendWz(AVIMClient client) {
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

    public static void sendImage1(final AVIMClient client, final String filePath){
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
                    Map< String, Object > attributes = new HashMap< String, Object >();
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

    public static void sendImage2(final AVIMClient client, final String filePath){
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

    public static void sendAudio1(final AVIMClient client, final String filePath){
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

    public static void sendAudio2(final AVIMClient client, final String filePath){
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

    public static void sendVideo1(final AVIMClient client, final String filePath){
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

    public static void sendVideo2(final AVIMClient client, final String filePath){
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

    public static void sendLocation(final AVIMClient client, final String filePath){
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

    public static void sendMessageToJerryFromTom(final String filePath,final int type) {
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
}
