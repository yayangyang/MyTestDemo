package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessage;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMReservedMessageType;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;
import com.avos.avoscloud.im.v2.messages.AVIMImageMessage;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.GsonUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.okhttpUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.bean.receiveMessage;
import mobiesafe74.itheima.com.immoc_recorder.manager.MediaManager;
import mobiesafe74.itheima.com.immoc_recorder.manager.VoiceManager;
import mobiesafe74.itheima.com.immoc_recorder.view.AudioRecorderButton;
import mobiesafe74.itheima.com.immoc_recorder.view.MyListView;

import static android.R.string.ok;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.os.Build.VERSION_CODES.M;
import static com.avos.avoscloud.im.v2.AVIMMessageManager.registerMessageHandler;
import static mobiesafe74.itheima.com.immoc_recorder.R.drawable.recorder;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.toolbar;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.GsonUtil.gson;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.getOneConversation;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.oldestMessage;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.isEnterMain;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.mAddOneConversationListener;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.myName;
import static mobiesafe74.itheima.com.immoc_recorder.view.MyListView.isEmpty;
import static mobiesafe74.itheima.com.immoc_recorder.view.MyListView.isScrolling;

public class ChatActivity extends BaseActivity implements View.OnClickListener{

    public static int LV_TO_LAST=0x11;
//    public static int SCROLLING=0x12;

    public static boolean isEnterChat=false;
    private boolean isCreateGroupChat;

    public static int index=-1;

    private MyListView mListView;
    private Toolbar mToolbar;
    private RecorderAdapter mAdapter;

    private List<Recorder> mDatas=new ArrayList<Recorder>();

    private AudioRecorderButton mAudioRecorderButton;
    private ImageView change_tovoice_btn,change_todazi_btn,fanhui;
    private EditText id_wbk;
    private Button fasong;
    private TextView name;

    private boolean pd=false;//判断是否点击过change_todazi_btn或文本框
    private boolean isOpen=false;

    public static String objectId="-1";
    public int size=0;
    public ArrayList mArrayList;

    private AVIMTypedMessageHandler mAVIMTypedMessageHandler;

    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==LV_TO_LAST){
                mListView.setSelection(mDatas.size()-1);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        initUI();
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        isEnterChat=true;
    }

    private void initUI() {
        mToolbar= (Toolbar) findViewById(toolbar);
        mListView= (MyListView) findViewById(R.id.id_listview);
        change_tovoice_btn= (ImageView) findViewById(R.id.change_tovoice_btn);
        change_todazi_btn= (ImageView) findViewById(R.id.change_todazi_btn);
        fanhui= (ImageView) findViewById(R.id.fanhui);
        fanhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        id_wbk= (EditText) findViewById(R.id.id_wbk);
        fasong= (Button) findViewById(R.id.fasong);
        mAudioRecorderButton= (AudioRecorderButton) findViewById(R.id.id_recorder_button);
        mAudioRecorderButton.setActivity(this);
        mAudioRecorderButton.setAudioFinishRecorderListener(new AudioRecorderButton.AudioFinishRecorderListener() {
            @Override
            public void onFinish(float seconds, String filePath) {
                if(objectId!="-1"){
                    Log.e("seconds",seconds+"");
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(currentTime);
                    Recorder recorder=new Recorder(seconds,filePath);
                    recorder.setSelf(true);
                    recorder.setType(Recorder.TYPE_VOICE);
                    MyDatabaseUtil.insertMessage(db,objectId,mArrayList.toString(),LoginActivity.myName,true,
                            "-3",filePath,seconds+"",dateString);
                    mDatas.add(recorder);
                    mAdapter.notifyDataSetChanged();
                    mListView.setSelection(mDatas.size()-1);
                    sendUtil.sendMessage(LoginActivity.myName,recorder,sendUtil.audio1,mArrayList,false,true);
                }else{
                    getOneConversation(mArrayList,false,true);//取得objectId
                }
            }
        });
        name = (TextView) findViewById(R.id.name);

        mAdapter=new RecorderAdapter(this,mDatas);
        mListView.setAdapter(mAdapter);
        mListView.setChatActivity(this);
        change_tovoice_btn.setOnClickListener(this);
        change_todazi_btn.setOnClickListener(this);
        id_wbk.setOnClickListener(this);
        fasong.setOnClickListener(this);
        sendUtil.setupdateMessageListener(new sendUtil.updateMessageListener() {
            @Override
            public void updateMessage(List<Recorder> messages) {
                Log.e("messages爱谁谁",messages.size()+"");
                ArrayList<Recorder> recorders = (ArrayList<Recorder>) messages;
                size=recorders.size();
                //下拉取得聊天记录时需改变VoiceManager的position,以此确保动画仍旧在对应的语音item播放
                if(VoiceManager.getPosition()!=-1) VoiceManager.setPosition(VoiceManager.getPosition()+size);
                mDatas.addAll(0,recorders);
                mAdapter.notifyDataSetChanged();
//                Log.e(oldestMessage+"dfdfdf","啥啥啥啥啥啥啥啥啥所");
                if(!MyListView.isScrolling){
                    mHandler.sendEmptyMessageDelayed(LV_TO_LAST,150);//组件绘画需要时间,跳转到最后一个需要延迟
                }else{
                    MyListView.isScrolling=false;
                    mListView.setSelection(size);//界面刷新时会全部显示,设置加载更多历史记录时仍在原地
                }
            }
        });

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String name = bundle.getString("friendName");
        mArrayList= (ArrayList) bundle.getStringArrayList("Members");
        Log.e("刚取到mArrayList",mArrayList.toString());
        this.name.setText(name);
        boolean isExistence = bundle.getBoolean("isExistence");
        objectId = bundle.getString("conversationId");
        isCreateGroupChat = bundle.getBoolean("isCreateGroupChat");
//        Log.e("objectId","33333333333");
        if(isExistence){
//            Log.e("objectId",objectId+"dd444444");
            index=MyDatabaseUtil.getMessageCount(db,objectId);
//            Log.e("index",index+"");
            if(index!=0){
                sendUtil.getMessage(10);
            }else{
                MyListView.isEmpty=true;
            }
        }else{
//            Log.e("objectId","22222222");
            MyListView.isEmpty=true;
            getOneConversation(mArrayList,false,true);//取得objectId
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.change_tovoice_btn){
            change_tovoice_btn.setVisibility(View.GONE);
            change_todazi_btn.setVisibility(View.VISIBLE);
            id_wbk.setVisibility(View.GONE);
            mAudioRecorderButton.setVisibility(View.VISIBLE);
            fasong.setVisibility(View.GONE);
            if(pd){
                jianpan();
                isOpen=false;
            }
        }else  if(v.getId()==R.id.change_todazi_btn){
            change_tovoice_btn.setVisibility(View.VISIBLE);
            change_todazi_btn.setVisibility(View.GONE);
            id_wbk.setVisibility(View.VISIBLE);
            mAudioRecorderButton.setVisibility(View.GONE);
            fasong.setVisibility(View.VISIBLE);
            pd=true;
            jianpan();
            id_wbk.requestFocus();
            mHandler.sendEmptyMessageDelayed(LV_TO_LAST,130);
            isOpen=true;
        }else  if(v.getId()==R.id.id_wbk){
            id_wbk.requestFocus();
            mHandler.sendEmptyMessageDelayed(LV_TO_LAST,130);
            pd=true;
            isOpen=true;
//            Log.e("getMeasuredHeight",id_wbk.getMeasuredHeight()+"");
//            Log.e("getHeight",id_wbk.getHeight()+"");
//            Log.e("getHeight",id_wbk.getBottom()+"");
//            Log.e("getHeight",id_wbk.getTop()+"");
        }else  if(v.getId()==R.id.fasong){
            String wz=id_wbk.getText().toString();
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateString = formatter.format(currentTime);
            if(wz.length()!=0){
                if(objectId!="-1"){
                    Recorder recorder=new Recorder(-1,"");
                    recorder.setSelf(true);
                    recorder.setType(Recorder.TYPE_WORD);
                    recorder.setWz(wz);
                    Log.e("文字",wz);
                    Log.e("mArrayList.toString()",mArrayList.toString());
                    ArrayList<String> arrayList= (ArrayList<String>) mArrayList.clone();
                    MyDatabaseUtil.insertMessage(db,objectId,arrayList.toString(),LoginActivity.myName,true,
                            "-1",wz,"-1",dateString);
                    mDatas.add(recorder);
                    mAdapter.notifyDataSetChanged();
                    mHandler.sendEmptyMessageDelayed(LV_TO_LAST,130);
                    id_wbk.setText("");
                    Log.e("isCreateGroupChat",isCreateGroupChat+"");
                    if(isCreateGroupChat){
                        sendUtil.sendWithGroup(objectId,recorder,sendUtil.wz);
                    }else{
                        sendUtil.sendMessage(LoginActivity.myName,recorder,sendUtil.wz,arrayList,false,true);
                    }
                }else{
                    getOneConversation(mArrayList,false,true);//取得objectId
                }
            }
        }
    }

    public void jianpan() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public boolean isOpen(){
        return isOpen;
    }

    public void reset(){
        isOpen=false;
        pd=false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==0){

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.chat, menu);//加载菜单布局
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAudioRecorderButton.reset();//主要目的是停止线程,防止内存泄漏
        MediaManager.release();
        oldestMessage=null;
        index=-1;
        MyListView.isEmpty=false;
        objectId="-1";
        MyListView.isScrolling=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        MediaManager.resume();
        mAVIMTypedMessageHandler=new AVIMTypedMessageHandler<AVIMTypedMessage>() {
            @Override
            public void onMessage(AVIMTypedMessage msg, AVIMConversation conv, AVIMClient client) {
                Log.e("王五","火狐啥啥啥");//自己接收不到自己的消息
                Log.e("ConversationId",conv.getConversationId()+"");
                Date currentTime = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateString = formatter.format(currentTime);
                if (conv.getConversationId().equals(objectId)) {
                    Log.e("www",msg.getMessageType()+"火狐");
                    if (msg.getMessageType()== -1){//文本消息
                        Recorder recorder=new Recorder(-1,"");
                        recorder.setSelf(false);
                        recorder.setType(Recorder.TYPE_WORD);
                        receiveMessage receiveMessage = GsonUtil.jiexi(msg.getContent());
                        recorder.setWz(receiveMessage._lctext);
                        Log.e("from",msg.getFrom());
                        MyDatabaseUtil.insertMessage(db,objectId,mArrayList.toString(),msg.getFrom(),false,
                                "-1",receiveMessage._lctext,"-1",dateString);
                        mDatas.add(recorder);
                        mAdapter.notifyDataSetChanged();
                        mHandler.sendEmptyMessageDelayed(LV_TO_LAST,130);
                    }else if (msg.getMessageType()== -2){//图像消息

                    }else if (msg.getMessageType()== -3){//音频消息
                        AVIMAudioMessage msg1= (AVIMAudioMessage) msg;
                        Map<String, Object> attrs = msg1.getAttrs();
                        String timeString = (String) attrs.get("time");
//                        Log.e("timeString",timeString);
                        float time=Float.parseFloat(timeString);
                        Recorder recorder=new Recorder(time,msg1.getFileUrl());
                        Log.e("FileUrl",msg1.getFileUrl());
                        recorder.setSelf(false);
                        recorder.setType(Recorder.TYPE_VOICE);
                        MyDatabaseUtil.insertMessage(db,objectId,mArrayList.toString(),msg.getFrom(),false,
                                "-3",msg1.getFileUrl(),timeString,dateString);
                        mDatas.add(recorder);
                        mAdapter.notifyDataSetChanged();
                        mHandler.sendEmptyMessageDelayed(LV_TO_LAST,130);
                    }else if (msg.getMessageType()== -4){//视频消息

                    }else if (msg.getMessageType()== -5){//位置消息

                    }else if (msg.getMessageType()== -6){//文件消息

                    }
                }else{
                    ArrayList<String> arrayList= new ArrayList<String>();
                    for(int i=0;i<conv.getMembers().size();i++){
                        arrayList.add(conv.getMembers().get(i));
                    }
                    boolean isGroupChat=true;
//                    Boolean unique = (Boolean) conv.get("unique");
//                    Log.e("unique-ChatActivity",unique+"");
                    if(conv.getMembers().size()<=2){//群聊也许可以通过数量来与单聊区分
                        arrayList.clear();
                        isGroupChat=false;
                        arrayList.add(LoginActivity.myName);
                        arrayList.add(msg.getFrom());
                    }
                    if(MyDatabaseUtil.getConversationCount(db,conv.getConversationId())<=0){
                        Log.e("arrayList",arrayList.toString());
                        MyDatabaseUtil.insertConversation(db,conv.getConversationId(),conv.getName(),
                                arrayList.toString(),conv.getCreator(), formatter.format(conv.getCreatedAt()),
                                formatter.format(conv.getUpdatedAt()),R.drawable.ic_launcher,isGroupChat);
                        WetChat wetchat = new WetChat();
                        wetchat.setLeftImage(R.drawable.ic_launcher);
                        wetchat.setCanVisivile(true);
                        wetchat.setName(conv.getName());
                        wetchat.setMessage("搜索");
                        wetchat.setTime(formatter.format(conv.getUpdatedAt()));
                        wetchat.setConversationId(conv.getConversationId());
                        wetchat.setMembers(arrayList.toString());
                        if(isEnterMain) mAddOneConversationListener.AddOneConversationListener(wetchat);
                    }

                    if (msg.getMessageType() == -1) {//文本消息
                        receiveMessage receiveMessage = GsonUtil.jiexi(msg.getContent());
                        MyDatabaseUtil.insertMessage(db, conv.getConversationId(), arrayList.toString(), msg.getFrom(), isGroupChat,
                                "-1", receiveMessage._lctext, "-1", dateString);
                    }else if (msg.getMessageType() == -3) {//音频消息
                        AVIMAudioMessage msg1= (AVIMAudioMessage) msg;
                        Map<String, Object> attrs = msg1.getAttrs();
                        String timeString = (String) attrs.get("time");
                        float time=Float.parseFloat(timeString);
                        MyDatabaseUtil.insertMessage(db,conv.getConversationId(),arrayList.toString(),msg.getFrom(),false,
                                "-3",msg1.getFileUrl(),timeString,dateString);
                    }
                }
            }
        };
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class,mAVIMTypedMessageHandler);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MediaManager.pause();
        AVIMMessageManager.unregisterMessageHandler(AVIMTypedMessage.class,mAVIMTypedMessageHandler);
    }

}
