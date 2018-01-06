package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.FrameLayout;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.holder.SelectGroupChatHolder;

/**
 * Created by Administrator on 2017/5/22.
 */

public class SelectGroupChatActivity extends BaseActivity {
    private Toolbar mToolbar;
    private FrameLayout container;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectgroupchat);

        init();
    }

    private void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("选择群聊");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.personal_return_btn);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        container= (FrameLayout) findViewById(R.id.container);
        SelectGroupChatHolder selectGroupChatHolder = new SelectGroupChatHolder();
        selectGroupChatHolder.initData();
        container.addView(selectGroupChatHolder.getRootView());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
