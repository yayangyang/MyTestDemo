package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.avos.avoscloud.im.v2.AVIMConversation;

import java.util.ArrayList;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.holder.InitiateGroupChatHolder;

import static com.avos.avoscloud.Messages.CommandType.conv;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil.mCreateGroupChatListener;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;

/**
 * Created by Administrator on 2017/5/22.
 */

public class InitiateGroupChatActivity extends BaseActivity {
    private Toolbar mToolbar;
    private FrameLayout container;
    private Button determine;
    private EditText addNewFriend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_groupchat);

        init();
    }

    private void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("发起群聊");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.personal_return_btn);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sendUtil.setCreateGroupChatListener(new sendUtil.CreateGroupChatListener() {
            @Override
            public void createGroupChatListener(AVIMConversation conv) {
                //创建成功才会跳转到ChatActivity
                Log.e("创建会话成功","创建会话成功");
                Intent intent = new Intent();
                intent.setClass(InitiateGroupChatActivity.this,ChatActivity.class);
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("Members",InitiateGroupChatHolder.mStringArrayList);
                bundle.putBoolean("isExistence",true);
                bundle.putString("conversationId",conv.getConversationId());
                ArrayList<String> arrayList = new ArrayList<>();
                arrayList.addAll(InitiateGroupChatHolder.mStringArrayList);
                arrayList.remove(LoginActivity.myName);
                bundle.putString("friendName",arrayList.toString());
                bundle.putBoolean("isCreateGroupChat",true);
                intent.putExtras(bundle);
                startActivity(intent);
                finish();
                InitiateGroupChatHolder.mStringArrayList.clear();
                if(mCloseGroupChatActivityListener!=null){
                    mCloseGroupChatActivityListener.closeGroupChatActivityListener();
                }
            }
        });
        determine= (Button) findViewById(R.id.determine);
        determine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(InitiateGroupChatHolder.mStringArrayList.size()==1){
                    if(InitiateGroupChatHolder.mStringArrayList.contains(LoginActivity.myName)){
                        //跟自己聊天
                        String friendName = InitiateGroupChatHolder.mStringArrayList.get(0);
                        singleChat(friendName);
                    }else{
                        //跟别人一对一聊天
                        String friendName = InitiateGroupChatHolder.mStringArrayList.get(0);
                        singleChat(friendName);
                    }
                }else if(InitiateGroupChatHolder.mStringArrayList.size()==2){
                    if(InitiateGroupChatHolder.mStringArrayList.contains(LoginActivity.myName)){
                        //跟别人一对一聊天
                        InitiateGroupChatHolder.mStringArrayList.remove(LoginActivity.myName);
                        String friendName = InitiateGroupChatHolder.mStringArrayList.get(0);
                        singleChat(friendName);
                    }else{
                        //建立群聊(微信可创建相同成员的群聊,这里也是)
                        Log.e(InitiateGroupChatHolder.mStringArrayList.size()+"wwww",InitiateGroupChatHolder.mStringArrayList+"");
                        sendUtil.getOneConversation(InitiateGroupChatHolder.mStringArrayList, false, false);
                    }
                }else if(InitiateGroupChatHolder.mStringArrayList.size()>=3){
                    //建立群聊(微信可创建相同成员的群聊,这里也是)
                    Log.e(InitiateGroupChatHolder.mStringArrayList.size()+"wwww",InitiateGroupChatHolder.mStringArrayList+"");
                    sendUtil.getOneConversation(InitiateGroupChatHolder.mStringArrayList, false, false);
                }
            }
        });
        container= (FrameLayout) findViewById(R.id.container);
        InitiateGroupChatHolder initiateGroupChatHolder = new InitiateGroupChatHolder();
        initiateGroupChatHolder.initData();
        container.addView(initiateGroupChatHolder.getRootView());
        addNewFriend = (EditText) container.findViewById(R.id.addNewFriend);
        addNewFriend.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(addNewFriend.getText().toString())){
                    determine.setEnabled(false);
                }else{
                    determine.setEnabled(true);
                }
            }
        });

//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams
//                (LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
//        addContentView(initiateGroupChatHolder.getRootView(),params);
    }

    public void singleChat(String friendName){
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        intent.setClass(InitiateGroupChatActivity.this,ChatActivity.class);
        ArrayList<String> list = new ArrayList<String>();
        if(LoginActivity.myName.equals(friendName)){
            list.add(LoginActivity.myName);
        }else{
            list.add(LoginActivity.myName);
            list.add(friendName);
        }
        String conversationId = MyDatabaseUtil.getConversationId(db, list);
        if(conversationId=="-1"){//返回-1表示本账号与此好友没有聊天记录
            bundle.putBoolean("isExistence",false);
            Log.e("没有聊天记录","没有聊天记录");
        }else{
            bundle.putBoolean("isExistence",true);
            bundle.putString("conversationId",conversationId+"");
        }
        bundle.putString("friendName",friendName);
        bundle.putStringArrayList("Members", list);
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
        InitiateGroupChatHolder.mStringArrayList.clear();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        sendUtil.mCreateGroupChatListener=null;
        mCloseGroupChatActivityListener=null;
    }

    public static CloseGroupChatActivityListener mCloseGroupChatActivityListener;

    public static void setCreateGroupChatListener(CloseGroupChatActivityListener m){
        mCloseGroupChatActivityListener=m;
    }

    public interface CloseGroupChatActivityListener{
        void closeGroupChatActivityListener();
    }

}
