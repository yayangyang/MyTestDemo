package mobiesafe74.itheima.com.immoc_recorder.Utils;

import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.avos.avoscloud.AVCloudQueryResult;
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVGeoPoint;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.CloudQueryCallback;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMConversationQuery;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageOption;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.Conversation;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationQueryCallback;
import com.avos.avoscloud.im.v2.callback.AVIMMessagesQueryCallback;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.avos.avoscloud.im.v2.messages.AVIMLocationMessage;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.avos.avoscloud.im.v2.messages.AVIMVideoMessage;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.InitiateGroupChatActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.bean.conversation;
import mobiesafe74.itheima.com.immoc_recorder.holder.InitiateGroupChatHolder;
import mobiesafe74.itheima.com.immoc_recorder.view.MyListView;

import static android.R.attr.type;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static com.avos.avoscloud.AVQuery.CachePolicy.CACHE_ELSE_NETWORK;
import static com.avos.avoscloud.Messages.CommandType.conv;
import static com.avos.avoscloud.Messages.OpType.members;
import static com.tencent.qc.stat.StatReportStrategy.f;
import static mobiesafe74.itheima.com.immoc_recorder.R.drawable.recorder;
import static mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity.index;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.myName;


/**
 * Created by Administrator on 2017/5/11.
 */

public class sendUtil {

//    private AVIMTypedMessageHandler handlerLocation;
    public static int wz=0;
    public static int image1=1;
    public static int image2=2;
    public static int audio1=3;
    public static int audio2=4;
    public static int video1=5;
    public static int video2=6;
    public static int location=7;
    public static int login=8;

    public static AVIMClient client;

    public static updateListener sUpdateListener;
    public static updateMessageListener sUpdateMessageListener;
    public static loginListener sLoginListener;
    public static AVIMMessage  oldestMessage;

    public static void sendWz(AVIMClient client, final Recorder recorder, final List<String> members, boolean isTransient, boolean isUnique) {
        // 创建与Jerry之间的对话
        //申请会话时会话的name字符串会被省略前后的空格,中间若有连续空格会被替换为一个空格(一个连续替换为一个)
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
//                        StringBuffer stringBuffer=new StringBuffer();
//                        for(int i=0;i<members.size();i++){
//                            stringBuffer.append(members.get(i));
//                        }
//                        Log.e("stringBuffer",stringBuffer.toString());
                        Log.e("members.toString()",members.toString());
//                        Log.e("www",e+"水水水水111");
                        if (e == null) {
//                            boolean isGroupChat=false;
//                            if(members.size()>2) isGroupChat=true;
//                            Log.e("ConversationId",conv.getConversationId());
//                            if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                        conv.getName(),conv.getMembers().toString(),
//                                        conv.getCreator(),conv.getCreatedAt().toString(),
//                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                            }
//                            Log.e("www","水水水水222");
                            AVIMTextMessage msg = new AVIMTextMessage();
                            msg.setText(recorder.getWz());
                            AVIMMessageOption messageOption = new AVIMMessageOption();
                            messageOption.setPushData("自定义离线消息推送内容");
                            // 发送消息
                            conv.sendMessage(msg,messageOption,new AVIMConversationCallback() {
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

    public static void sendWzWithGroup(AVIMConversation conv, Recorder recorder) {
        // 发送消息
        AVIMTextMessage msg = new AVIMTextMessage();
        msg.setText(recorder.getWz());
        conv.sendMessage(msg,new AVIMConversationCallback() {
            @Override
            public void done(AVIMException e) {
                if (e == null) {
                    Log.e("GroupChatText", "发送成功！");
                }
            }
        });
    }

    public static void sendImage1(final AVIMClient client, final Recorder recorder, final List<String> members,
                                  boolean isTransient, boolean isUnique){
        //登录成功
        // 创建对话，默认创建者是在包含在成员列表中的
        client.createConversation(members,members.toString(),null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
            @Override
            public void done(AVIMConversation conv, AVIMException e) {
                if (e == null) {
//                    boolean isGroupChat=false;
//                    if(members.size()>2) isGroupChat=true;
//                    if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                        MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                conv.getName(),conv.getMembers().toString(),
//                                conv.getCreator(),conv.getCreatedAt().toString(),
//                                conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                    }
                    AVIMImageMessage picture = null;
                    try {
                        picture = new AVIMImageMessage(recorder.getFilePath());
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

    public static void sendImage2(final AVIMClient client, final Recorder recorder, final List<String> members,
                                  boolean isTransient, boolean isUnique){
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
//                            boolean isGroupChat=false;
//                            if(members.size()>2) isGroupChat=true;
//                            if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                        conv.getName(),conv.getMembers().toString(),
//                                        conv.getCreator(),conv.getCreatedAt().toString(),
//                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                            }
                            AVFile file =new AVFile("萌妹子",recorder.getFilePath(), null);
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

    public static void sendAudio1(final AVIMClient client, final Recorder recorder, final List<String> members,
                                  boolean isTransient, boolean isUnique){
        // 创建名为“猫和老鼠”的对话
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
//                            boolean isGroupChat=false;
//                            if(members.size()>2) isGroupChat=true;
//                            if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                        conv.getName(),conv.getMembers().toString(),
//                                        conv.getCreator(),conv.getCreatedAt().toString(),
//                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                            }
                            AVFile file = null;
                            try {
//                                file = AVFile.withAbsoluteLocalPath("sound01.mp3",filePath);
                                file = AVFile.withAbsoluteLocalPath("随便填",recorder.getFilePath());
                            } catch (FileNotFoundException e1) {
                                e1.printStackTrace();
                            }
                            AVIMAudioMessage m = new AVIMAudioMessage(file);
                            Map<String, Object> attrs=new HashMap<String, Object>();
                            attrs.put("time",recorder.getTime()+"");
                            m.setAttrs(attrs);
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

    public static void sendAudio2(final AVIMClient client, final Recorder recorder, final List<String> members,
                                  boolean isTransient, boolean isUnique){
        // 创建名为「猫和老鼠」的对话
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
//                            boolean isGroupChat=false;
//                            if(members.size()>2) isGroupChat=true;
//                            if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                        conv.getName(),conv.getMembers().toString(),
//                                        conv.getCreator(),conv.getCreatedAt().toString(),
//                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                            }
                            AVFile file = new AVFile("music", recorder.getFilePath(), null);
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

    public static void sendVideo1(final AVIMClient client, final Recorder recorder, final List<String> members,
                                  boolean isTransient, boolean isUnique){
        // 创建名为“猫和老鼠”的对话
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        Log.e("www","搜索1");
                        if (e == null) {
//                            boolean isGroupChat=false;
//                            if(members.size()>2) isGroupChat=true;
//                            if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                        conv.getName(),conv.getMembers().toString(),
//                                        conv.getCreator(),conv.getCreatedAt().toString(),
//                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                            }
                            Log.e("www","搜索2");
                            AVFile file = null;
                            try {
                                file = AVFile.withAbsoluteLocalPath("bbc_奶酪.mp4", recorder.getFilePath());
                                Log.e("www","搜索3");
                            } catch (FileNotFoundException e1) {
                                Log.e("www","搜索4");
                                e1.printStackTrace();
                            }
                            Log.e("www","搜索6");
                            AVIMVideoMessage m = new AVIMVideoMessage(file);
                            Log.e("www","搜索7"+recorder.getFilePath());
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

    public static void sendVideo2(final AVIMClient client, final Recorder recorder, final List<String> members,
                                  boolean isTransient, boolean isUnique){
        // 创建名为「猫和老鼠」的对话
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
//                        boolean isGroupChat=false;
//                        if(members.size()>2) isGroupChat=true;
//                        if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                            MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                    conv.getName(),conv.getMembers().toString(),
//                                    conv.getCreator(),conv.getCreatedAt().toString(),
//                                    conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                        }
                        if (e == null) {
                            AVFile file = new AVFile("video",recorder.getFilePath(), null);
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

    public static void sendLocation(final AVIMClient client, final Recorder recorder, final List<String> members,
                                    boolean isTransient, boolean isUnique){
        // 创建名为「猫和老鼠」的对话
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        if (e == null) {
//                            boolean isGroupChat=false;
//                            if(members.size()>2) isGroupChat=true;
//                            if(MyDatabaseUtil.getCount(db,conv.getConversationId())<=0){
//                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
//                                        conv.getName(),conv.getMembers().toString(),
//                                        conv.getCreator(),conv.getCreatedAt().toString(),
//                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
//                            }
                            final AVIMLocationMessage locationMessage=new AVIMLocationMessage();
                            String[] strings = recorder.getFilePath().split(",");
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

    //发送消息
    public static void sendMessage(final String myName, final Recorder recorder, final int type, final List<String> members,
                                   final boolean isTransient, final boolean isUnique ) {
        if(type==login){
            // 用自己的名字作为clientId，获取AVIMClient对象实例
            client = AVIMClient.getInstance(myName);
            Log.e("ww","client");
            // 与服务器连接
            client.open(new AVIMClientCallback() {
                @Override
                public void done(AVIMClient client, AVIMException e) {
                    if (e == null) {
                        Log.e(myName,"登录成功!");
                        AVQuery query = new AVQuery("friend");
                        final AVQuery<AVObject> avObjectAVQuery = query.whereEqualTo("belongto",LoginActivity.myName);
                        avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
                            @Override
                            public void done(List<AVObject> list, AVException e) {
                                if(e==null&&list.size()>0){
                                    Log.e(myName,"读取好友列表成功");
                                    MyDatabaseUtil.deleteAllFriend(LoginActivity.db);
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                    for(int i=0;i<list.size();i++){
                                        String friend = (String) list.get(i).get("friendName");
                                        Date createdAt = (Date) list.get(i).get("createdAt");
                                        Date updatedAt = (Date) list.get(i).get("updatedAt");
                                        String createString = formatter.format(createdAt);
                                        String updateString = formatter.format(updatedAt);
                                        MyDatabaseUtil.insertUser(LoginActivity.db,myName,friend,createString,updateString);
                                    }
                                    sLoginListener.login();
                                    sLoginListener=null;//防止内存泄漏
                                }else if(e!=null){
                                    Log.e(myName,"读取好友列表失败");
                                    sLoginListener.login();
                                }else if(list.size()==0){
                                    Log.e(myName,"好友列表没有数据");
                                    sLoginListener.login();
                                }
                            }
                        });
                    }else{
                        Log.e(myName,"登录失败!");
                    }
                }
            });
        }else{
            if(client!=null){
                if(type==wz){
                    Log.e("文字111111",recorder.getWz());
                    sendWz(client,recorder,members,isTransient,isUnique);
                }else if(type==image1){
                    sendImage1(client,recorder,members,isTransient,isUnique);
                }else if(type==image2){
                    sendImage2(client,recorder,members,isTransient,isUnique);
                }else if(type==audio1){
                    sendAudio1(client,recorder,members,isTransient,isUnique);
                }else if(type==audio2){
                    sendAudio2(client,recorder,members,isTransient,isUnique);
                }else if(type==video1){
                    sendVideo1(client,recorder,members,isTransient,isUnique);
                }else if(type==video2){
                    sendVideo2(client,recorder,members,isTransient,isUnique);
                }else if(type==location){
                    sendLocation(client,recorder,members,isTransient,isUnique);
                }
            }else{
                Toast.makeText(UIUtils.getContext(),"client为空",Toast.LENGTH_SHORT).show();
            }
        }
    }

    public static void sendWithGroup(final String conversationId, final Recorder recorder, final int type){
        client.open(new AVIMClientCallback(){
            @Override
            public void done(final AVIMClient client, AVIMException e){
                if(e==null){
                    //登录成功
                    AVIMConversationQuery query = client.getQuery();
                    query.whereEqualTo("objectId",conversationId);
                    query.findInBackground(new AVIMConversationQueryCallback(){
                        @Override
                        public void done(List<AVIMConversation> convs,AVIMException e){
                            if(e==null){
                                if(convs!=null && !convs.isEmpty()){
                                    //convs.get(0) 就是想要的conversation
                                    AVIMConversation conv = convs.get(0);
                                    if(client!=null){
                                        if(type==wz){
                                            sendWzWithGroup(conv,recorder);
                                        }else if(type==image1){
//                                            sendImage1(client,recorder,members,isTransient,isUnique);
                                        }else if(type==image2){
//                                            sendImage2(client,recorder,members,isTransient,isUnique);
                                        }else if(type==audio1){
//                                            sendAudio1(client,recorder,members,isTransient,isUnique);
                                        }else if(type==audio2){
//                                            sendAudio2(client,recorder,members,isTransient,isUnique);
                                        }else if(type==video1){
//                                            sendVideo1(client,recorder,members,isTransient,isUnique);
                                        }else if(type==video2){
//                                            sendVideo2(client,recorder,members,isTransient,isUnique);
                                        }else if(type==location){
//                                            sendLocation(client,recorder,members,isTransient,isUnique);
                                        }
                                    }else{
                                        Toast.makeText(UIUtils.getContext(),"client为空-GroupChat",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        }
                    });
                }
            }
        });
    }

    //获取对话
    public static void getConversation(int number){
        List<conversation> conversation = MyDatabaseUtil.getConversation(db, number + "");
        Log.e("conversation","conversation"+number);
        sUpdateListener.updateList(conversation);
    }

    //获取消息
    public static void getMessage(int number){
        if(!MyListView.isEmpty){
            ChatActivity.index-=number;
            int index=ChatActivity.index;
            if(index<=0){
                MyListView.isEmpty=true;
                index=0;
            }
            Log.e("index",index+"");
            Log.e("number",number+"");
            List<Recorder> message = MyDatabaseUtil.getMessage(db, ChatActivity.objectId, index + "", number + "");
            sUpdateMessageListener.updateMessage(message);
        }
    }

    //创建或取得已存在会话
    public static void getOneConversation(final List<String> members, boolean isTransient, final boolean isUnique) {
        client.createConversation(members, members.toString(), null,isTransient,isUnique,
                new AVIMConversationCreatedCallback() {
                    @Override
                    public void done(AVIMConversation conv, AVIMException e) {
                        //在这里面出现异常可能会从这里开始重走一遍这个方法
//                        Log.e("members",members+"啥啥啥啥啥啥啥啥啥所所所所所所所所所所所所");
//                        Log.e("members.toString()",members.toString()+"啥啥啥啥啥啥啥啥啥所所所所所所所所所所所所");
//                        Log.e("members.size()",members.size()+"");
                        if (e == null) {
                            boolean isGroupChat=false;
                            if(members.size()>2||!isUnique) isGroupChat=true;
                            Log.e("ConversationId",conv.getConversationId());
//                            Log.e("members",conv.getMembers().toString());
                            if(MyDatabaseUtil.getConversationCount(db,conv.getConversationId())<=0){
                                MyDatabaseUtil.insertConversation(db,conv.getConversationId(),
                                        conv.getName(),members.toString(),
                                        conv.getCreator(),conv.getCreatedAt().toString(),
                                        conv.getUpdatedAt().toString(), R.drawable.ic_launcher,isGroupChat);
                            }
                            ChatActivity.objectId=conv.getConversationId();
                            Toast.makeText(UIUtils.getContext(),"成功", Toast.LENGTH_SHORT).show();
                            if(mCreateGroupChatListener!=null&&isGroupChat==true){
                                mCreateGroupChatListener.createGroupChatListener(conv);
                            }
                        }else{
                            Toast.makeText(UIUtils.getContext(),"申请会话失败,请检查网络是否连接", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public static CreateGroupChatListener mCreateGroupChatListener;

    public static void setCreateGroupChatListener(CreateGroupChatListener m){
        mCreateGroupChatListener=m;
    }

    public interface CreateGroupChatListener{
        void createGroupChatListener(AVIMConversation conv);
    }

    public static void setupdateMessageListener(updateMessageListener listener){
        sUpdateMessageListener=listener;
    }

    public interface updateMessageListener{
        void updateMessage(List<Recorder> messages);
    }

    public static void setupdateListener(updateListener listener){
        sUpdateListener=listener;
    }

    public interface updateListener{
        void updateList(List<conversation> convs);
    }

    public static void setLoginListener(loginListener listener){
        sLoginListener=listener;
    }

    public interface loginListener{
        void login();
    }

    public void ceshi(){
        AVIMClient tom = AVIMClient.getInstance("Tom");
        tom.open(new AVIMClientCallback() {
            @Override
            public void done(AVIMClient client, AVIMException e) {
                if (e == null) {
                    // 创建名为“猫和老鼠”的对话
                    client.createConversation(Arrays.asList("Jerry"), "猫和老鼠", null,
                            new AVIMConversationCreatedCallback() {
                                @Override
                                public void done(AVIMConversation conv, AVIMException e) {
                                    if (e == null) {
                                        AVIMTextMessage msg = new AVIMTextMessage();
                                        msg.setText("耗子，起床！");

                                        AVIMMessageOption messageOption = new AVIMMessageOption();
                                        messageOption.setPushData("自定义离线消息推送内容");
                                        conv.sendMessage(msg, messageOption, new AVIMConversationCallback() {
                                            @Override
                                            public void done(AVIMException e) {
                                                if (e == null) {
                                                    // 发送成功
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

}
