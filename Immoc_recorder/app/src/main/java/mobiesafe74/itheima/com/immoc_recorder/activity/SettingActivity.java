package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.manager.DialogManager;

/**
 * Created by Administrator on 2017/5/26.
 */

public class SettingActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    private Dialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        init();
    }

    private void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("设置");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.personal_return_btn);
        setSupportActionBar(mToolbar);
        RelativeLayout newMessageReminder = (RelativeLayout) findViewById(R.id.newMessageReminder);
        newMessageReminder.setOnClickListener(this);

        RelativeLayout notDisturb = (RelativeLayout) findViewById(R.id.notDisturb);
        newMessageReminder.setOnClickListener(this);

        RelativeLayout chat = (RelativeLayout) findViewById(R.id.chat);
        chat.setOnClickListener(this);

        RelativeLayout privacy = (RelativeLayout) findViewById(R.id.privacy);
        privacy.setOnClickListener(this);

        RelativeLayout currency = (RelativeLayout) findViewById(R.id.currency);
        currency.setOnClickListener(this);

        RelativeLayout accountAndSecurity = (RelativeLayout) findViewById(R.id.accountAndSecurity);
        accountAndSecurity.setOnClickListener(this);

        RelativeLayout aboutWebChat = (RelativeLayout) findViewById(R.id.aboutWebChat);
        aboutWebChat.setOnClickListener(this);

        RelativeLayout helpAndFeedback = (RelativeLayout) findViewById(R.id.helpAndFeedback);
        helpAndFeedback.setOnClickListener(this);

        RelativeLayout laboratory = (RelativeLayout) findViewById(R.id.laboratory);
        laboratory.setOnClickListener(this);

        RelativeLayout exit = (RelativeLayout) findViewById(R.id.exit);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.newMessageReminder){

        }else if(v.getId()==R.id.notDisturb){

        }else if(v.getId()==R.id.chat){

        }else if(v.getId()==R.id.privacy){

        }else if(v.getId()==R.id.currency){

        }else if(v.getId()==R.id.accountAndSecurity){

        }else if(v.getId()==R.id.aboutWebChat){

        }else if(v.getId()==R.id.helpAndFeedback){

        }else if(v.getId()==R.id.laboratory){

        }else if(v.getId()==R.id.exit){
            Log.e("exit","退出");
            DialogManager dialogManager = new DialogManager(SettingActivity.this);
            dialog = dialogManager.showExitDialog();
        }
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
        if(dialog!=null){
            dialog.dismiss();
        }
    }
}
