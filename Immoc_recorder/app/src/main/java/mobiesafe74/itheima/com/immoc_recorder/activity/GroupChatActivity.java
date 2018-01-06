package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.avos.avoscloud.im.v2.AVIMConversation;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.holder.GroupChatHolder;

/**
 * Created by Administrator on 2017/5/25.
 */

public class GroupChatActivity extends BaseActivity {
    private Toolbar mToolbar;
    private FrameLayout container;
    private GroupChatHolder groupChatHolder;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groupchat);

        init();
    }

    private void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("群聊");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.personal_return_btn);
        container= (FrameLayout) findViewById(R.id.container);
        groupChatHolder = new GroupChatHolder();
        groupChatHolder.initData();
        container.addView(groupChatHolder.getRootView());
        InitiateGroupChatActivity.mCloseGroupChatActivityListener=new InitiateGroupChatActivity.CloseGroupChatActivityListener() {
            @Override
            public void closeGroupChatActivityListener() {
                finish();
            }
        };
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.groupchat, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==R.id.add_newgroupchat){
            Intent intent = new Intent();
            intent.setClass(GroupChatActivity.this,InitiateGroupChatActivity.class);
            startActivityForResult(intent,0);
        }
        return super.onOptionsItemSelected(item);
    }

}
