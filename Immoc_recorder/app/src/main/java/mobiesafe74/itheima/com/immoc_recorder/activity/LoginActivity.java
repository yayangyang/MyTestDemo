package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMMessageManager;
import com.avos.avoscloud.im.v2.AVIMTypedMessage;
import com.avos.avoscloud.im.v2.AVIMTypedMessageHandler;
import com.avos.avoscloud.im.v2.messages.AVIMAudioMessage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.GsonUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.bean.receiveMessage;
import mobiesafe74.itheima.com.immoc_recorder.holder.InitiateGroupChatHolder;
import mobiesafe74.itheima.com.immoc_recorder.manager.VoiceManager;
import mobiesafe74.itheima.com.immoc_recorder.view.AudioRecorderButton;
import mobiesafe74.itheima.com.immoc_recorder.view.MyListView;

import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.oldestMessage;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.sLoginListener;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.sUpdateListener;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.sUpdateMessageListener;

/**
 * Created by Administrator on 2017/5/11.
 */

public class LoginActivity extends AppCompatActivity {

    public static String myName;//下面以tom赋值,事实上这时唯一标识,类似qq号微信号的东西
//    public static boolean isLogin=false;
    private MyDatabaseUtil mMyDatabaseUtil;
    public static SQLiteDatabase db;
    public static AVIMTypedMessageHandler mAVIMTypedMessageHandler;
    public static boolean isEnterMain=false;//判断是否已进入MainActivity
    public static boolean isFirstEnterMain=true;//判断是否第一次进入MainActivity
    private Button username_login_button,username_register_button;
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private ProgressBar login_progress;
    private LinearLayout email_login_form;

    public static MainActivity sMainActivity=null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
    }

    private void init() {
        mMyDatabaseUtil=new MyDatabaseUtil(this,"chat",null,1);
        db=mMyDatabaseUtil.getWritableDatabase();
        username_login_button= (Button) findViewById(R.id.username_login_button);
        username_register_button= (Button) findViewById(R.id.username_register_button);
        mUsernameView= (AutoCompleteTextView) findViewById(R.id.username);
        mPasswordView= (EditText) findViewById(R.id.password);
        login_progress= (ProgressBar) findViewById(R.id.login_progress);
        email_login_form= (LinearLayout) findViewById(R.id.email_login_form);
        RegisterActivity.setCloseLoginActivityListener(new RegisterActivity.CloseLoginActivityListener() {
            @Override
            public void closeLoginActivityListener() {
                finish();
            }
        });
    }

    public void attemptLogin(View view) {
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        final String username = mUsernameView.getText().toString();
        final String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        Log.e("password",TextUtils.isEmpty(password)+"");
        Log.e("password",isPasswordValid(password)+"");
        Log.e("cancel",cancel+"");
        if (TextUtils.isEmpty(username)) {
            mUsernameView.setError(getString(R.string.error_field_required));
            focusView = mUsernameView;
            cancel = true;
        }

        if (cancel) {
            focusView.requestFocus();
        } else {
            showProgress(true);
            AVUser.logInInBackground(username, password, new LogInCallback<AVUser>() {
                @Override
                public void done(AVUser avUser, AVException e) {
                    if (e == null) {
                        login(username);
                    } else {
                        showProgress(false);
                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void register(View view){
        Intent intent = new Intent();
        intent.setClass(LoginActivity.this,RegisterActivity.class);
        startActivity(intent);
    }

    public void login(String username){
        Log.e("client",sendUtil.client+"");
        myName=username;
        sendUtil.sendMessage(myName,null,sendUtil.login,null,false,true);//记得加上登录成功再继续别的判断
        sendUtil.setLoginListener(new sendUtil.loginListener(){
            @Override
            public void login() {
                //在这里写数据有时不能显示数据
//                for(int i=0;i<15;i++){
//                    sendUtil.sendMessage(myName,"搜索",sendUtil.wz, Arrays.asList("jerry"),false,true);
//                }
                Intent intent = new Intent();
                intent.setClass(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void showProgress(boolean b) {
        if(b){
            login_progress.setVisibility(View.VISIBLE);
            email_login_form.setVisibility(View.GONE);
        }else{
            login_progress.setVisibility(View.GONE);
            email_login_form.setVisibility(View.VISIBLE);
        }
    }

    //密码的限制(如:大于四位才可)
    public boolean isPasswordValid(String pass){
        Log.e("length",pass.length()+"");
        if(pass.length()>4) return  true;
        return false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialization();
        mAVIMTypedMessageHandler=new AVIMTypedMessageHandler<AVIMTypedMessage>() {//默认最多20条离线消息
            @Override
            public void onMessage(AVIMTypedMessage msg, AVIMConversation conv, AVIMClient client) {
                try {
                    Log.e("收到了",msg.getContent());
                    Date currentTime = new Date();
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String dateString = formatter.format(currentTime);

                    ArrayList<String> arrayList= new ArrayList<String>();
                    for(int i=0;i<conv.getMembers().size();i++){
                        arrayList.add(conv.getMembers().get(i));
                    }
                    boolean isGroupChat=true;
//                Log.e("size",conv.getMembers().size()+"");
                    if(conv.getMembers().size()<=2){//群聊也许可以通过数量来与单聊区分
                        arrayList.clear();
                        isGroupChat=false;
                        arrayList.add(LoginActivity.myName);
                        arrayList.add(msg.getFrom());
                    }
                    if(MyDatabaseUtil.getConversationCount(db,conv.getConversationId())<=0){
                        //这里有错
//                    Log.e("arrayList",arrayList.toString());
//                    Log.e("conv",conv.getConversationId()+"www");
                        if(conv.getCreator()==null){
                            Log.e("getCreator","为空");
                        }else{
                            Log.e("getCreator","不为空");
                        }
//                    Log.e("conv.getCreator()",conv.getCreator());
//                    Log.e("conv.getCreatedAt()",conv.getCreatedAt()+"");
//                    Log.e("conv.getUpdatedAt()",conv.getUpdatedAt()+"");
//                    Log.e("isGroupChat",isGroupChat+"");
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
                        //isGroupChat之前没赋值,导致从主界面进入ChatActivity时isGroupChat为false,
                        //使用了不是群聊的逻辑,从而会多新建一个独特的会话(ubique为true)
                        wetchat.setGroupChat(isGroupChat);
                        if(isEnterMain) mAddOneConversationListener.AddOneConversationListener(wetchat);
                    }
                    Log.e("msg","11111111111");
                    if (msg.getMessageType() == -1) {//文本消息
                        Log.e("msg","222222222");
                        receiveMessage receiveMessage = GsonUtil.jiexi(msg.getContent());
                        Log.e("msg","33333333333");
                        MyDatabaseUtil.insertMessage(db, conv.getConversationId(), arrayList.toString(), msg.getFrom(), false,
                                "-1", receiveMessage._lctext, "-1", dateString);
                        Log.e("msg","4444444444444");
                    }else if (msg.getMessageType() == -3) {//音频消息
                        AVIMAudioMessage msg1= (AVIMAudioMessage) msg;
                        Map<String, Object> attrs = msg1.getAttrs();
                        String timeString = (String) attrs.get("time");
                        float time=Float.parseFloat(timeString);
                        MyDatabaseUtil.insertMessage(db,conv.getConversationId(),arrayList.toString(),msg.getFrom(),false,
                                "-3",msg1.getFileUrl(),timeString,dateString);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
//        Log.e("nnnnn","vvvvvv");
        AVIMMessageManager.registerMessageHandler(AVIMTypedMessage.class,mAVIMTypedMessageHandler);
    }

    private void initialization() {
        myName="";
        isEnterMain=false;
        isFirstEnterMain=true;
        ChatActivity.isEnterChat=false;
        InitiateGroupChatActivity.mCloseGroupChatActivityListener=null;
        MainActivity.rb_position=0;
        PersonalDataActivity.mCloseAddNewFriendActivityListener=null;
        PersonalDataActivity.mCloseSecondAddNewFriendActivityListener=null;
        RegisterActivity.mCloseLoginActivityListener=null;
        RecorderAdapter.mMaxItemWidth=0;
        RecorderAdapter.mMinItemWidth=0;
        InitiateGroupChatHolder.mStringArrayList=new ArrayList<String>();
        VoiceManager.position=-1;
        VoiceManager.mAnimView=null;
        sendUtil.client=null;
        sUpdateListener=null;
        sUpdateMessageListener=null;
        sLoginListener=null;
        oldestMessage=null;
        AudioRecorderButton.mCurrentState=AudioRecorderButton.STATE_NORMAL;
        MyListView.isEmpty=false;
        MyListView.isScrolling=false;
        sMainActivity=null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("LoginActivity","onPause");//执行onDestroy之前会执行onPause
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("LoginActivity","onDestroy");
//        //onPause之后才会执行接收离线消息操作,ondestroy在执行接收离线消息操作之后,所以解除注册放在ondestroy
//        AVIMMessageManager.unregisterMessageHandler(AVIMTypedMessage.class,mAVIMTypedMessageHandler);
    }

    public static AddOneConversationListener mAddOneConversationListener;

    public static void setAddOneConversationListener(AddOneConversationListener m){
        mAddOneConversationListener=m;
    }

    public interface AddOneConversationListener{
        void AddOneConversationListener(WetChat wetChat);
    }

}
