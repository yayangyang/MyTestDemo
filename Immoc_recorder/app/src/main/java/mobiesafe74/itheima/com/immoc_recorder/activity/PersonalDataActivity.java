package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;

import java.util.ArrayList;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil;
import mobiesafe74.itheima.com.immoc_recorder.holder.BottomDialogHolder;

import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;

/**
 * Created by Administrator on 2017/5/15.
 */

public class PersonalDataActivity extends BaseActivity{
    private Toolbar toolbar;
    private Button send_message,video_chat,addToAddressBook;
    private View.OnClickListener mOnClickListener;
    private TextView name;
    private String createdAt,updatedAt;
    private LinearLayout ll_01;
    private Boolean isFriend=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data);

        initUI();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String username = bundle.getString("username");
        createdAt = bundle.getString("createdAt");
        updatedAt = bundle.getString("updatedAt");
        isFriend=bundle.getBoolean("isFriend");
        name.setText(username);
        if(isFriend){
            ll_01.setVisibility(View.VISIBLE);
        }else{
            addToAddressBook.setVisibility(View.VISIBLE);
        }
    }

    private void initUI() {
        ll_01= (LinearLayout) findViewById(R.id.ll_01);
        toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("详细资料");
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setNavigationIcon(R.drawable.personal_return_btn);
        Drawable drawable = getDrawable(R.drawable.ic_launcher);
        toolbar.setOverflowIcon(drawable);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mOnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {//发送消息
                if(v.getId()==R.id.send_message){
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    intent.setClass(PersonalDataActivity.this,ChatActivity.class);
                    String friendName=name.getText().toString();
                    ArrayList<String> list = new ArrayList<String>();
                    //这里的顺序可能有问题,不解决根本无法实现群聊
                    if(LoginActivity.myName.equals(friendName)){
                        list.add(LoginActivity.myName);
                    }else{
                        list.add(LoginActivity.myName);
                        list.add(friendName);
                    }
                    String conversationId = MyDatabaseUtil.getConversationId(db, list);
//                    Collections.reverse(list);
//                    conversationId = MyDatabaseUtil.getConversationId(db, list);
//                    Log.e("conversationId",conversationId+"");
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
                    if(mCloseAddNewFriendActivityListener!=null){
                        mCloseAddNewFriendActivityListener.closeAddNewFriendActivityListener();
                    }
                    if(mCloseSecondAddNewFriendActivityListener!=null){
                        mCloseSecondAddNewFriendActivityListener.closeAddNewFriendActivityListener();
                    }
                }else if(v.getId()==R.id.video_chat){//视频聊天

                }else if(v.getId()==R.id.addToAddressBook){
                    //添加到通讯录(由于不清楚点击通讯录会发生什么,先省略申请好友步骤,点击即可完成添加)
                    Log.e("添加新朋友到通讯录","添加"+name.getText().toString()+"到通讯录");
                    AVObject query = new AVObject("friend");
                    query.put("belongto", LoginActivity.myName);
                    query.put("friendName", name.getText().toString());
                    query.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            if (e == null) {
                                Log.e("添加新朋友到通讯录","存储成功");
                                MyDatabaseUtil.insertUser(db,LoginActivity.myName,name.getText().toString(),
                                        createdAt,updatedAt);
                                finish();
                            } else {
                                Log.e("添加新朋友到通讯录","存储失败,请检查网络环境以及SDK配置是否正确");
                            }
                        }
                    });
                }
            }
        };
        send_message= (Button) findViewById(R.id.send_message);
        send_message.setOnClickListener(mOnClickListener);
        video_chat= (Button) findViewById(R.id.video_chat);
        video_chat.setOnClickListener(mOnClickListener);
        addToAddressBook= (Button) findViewById(R.id.addToAddressBook);
        addToAddressBook.setOnClickListener(mOnClickListener);
        name= (TextView) findViewById(R.id.name);
    }

    private void setDialog() {
//        View.OnClickListener onClickListener=new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(v.getId()==R.id.setMark){
//                    Toast.makeText(getApplicationContext(), "请选择照片", Toast.LENGTH_SHORT).show();
//                }else if(v.getId()==R.id.markFriend){
//                    Toast.makeText(getApplicationContext(), "即将打开相机", Toast.LENGTH_SHORT).show();
//                }else if(v.getId()==R.id.setFriendPlacePermission){
//                    Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
//                }else if(v.getId()==R.id.sendCard){
//                    Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
//                }
//            }
//        };
        Dialog mCameraDialog = new Dialog(this, R.style.BottomDialog);
        BottomDialogHolder BottomDialogHolder = new BottomDialogHolder();
        View root = BottomDialogHolder.getRootView();
        BottomDialogHolder.initData();
        //初始化视图
//        root.findViewById(R.id.setMark).setOnClickListener(onClickListener);
//        root.findViewById(R.id.markFriend).setOnClickListener(onClickListener);
//        root.findViewById(R.id.setFriendPlacePermission).setOnClickListener(onClickListener);
//        root.findViewById(R.id.sendCard).setOnClickListener(onClickListener);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
//        dialogWindow.setWindowAnimations(R.style.dialogstyle); // 添加动画
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        lp.height = (int) getResources().getDisplayMetrics().heightPixels/2; // 高度
//        root.measure(0, 0);
//        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search, menu);
        getMenuInflater().inflate(R.menu.yuan, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }if(item.getItemId()==R.id.yuan){
            setDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public static CloseAddNewFriendActivityListener mCloseAddNewFriendActivityListener;
    public static CloseAddNewFriendActivityListener mCloseSecondAddNewFriendActivityListener;

    public static void setCloseAddNewFriendActivityListener(CloseAddNewFriendActivityListener l){
        mCloseAddNewFriendActivityListener=l;
    }

    public static void setCloseSecondAddNewFriendActivityListener(CloseAddNewFriendActivityListener l){
        mCloseSecondAddNewFriendActivityListener=l;
    }

    public interface CloseAddNewFriendActivityListener{
        void closeAddNewFriendActivityListener();
    }

}
