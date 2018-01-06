package mobiesafe74.itheima.com.immoc_recorder.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import mobiesafe74.itheima.com.immoc_recorder.R;

/**
 * Created by Administrator on 2017/5/26.
 */

public class PersonalActivity extends BaseActivity implements View.OnClickListener{
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        init();
    }

    private void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("个人信息");
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.drawable.personal_return_btn);
        setSupportActionBar(mToolbar);

        RelativeLayout headPortrait = (RelativeLayout) findViewById(R.id.headPortrait);
        RelativeLayout nickname = (RelativeLayout) findViewById(R.id.nickname);
        RelativeLayout wechatNumber = (RelativeLayout) findViewById(R.id.wechatNumber);
        RelativeLayout QRcode = (RelativeLayout) findViewById(R.id.QRcode);
        LinearLayout address = (LinearLayout) findViewById(R.id.address);
        RelativeLayout gender = (RelativeLayout) findViewById(R.id.gender);
        RelativeLayout region = (RelativeLayout) findViewById(R.id.region);
        RelativeLayout personalizedSignature = (RelativeLayout) findViewById(R.id.personalizedSignature);

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.headPortrait){

        }else if(v.getId()==R.id.nickname){

        }else if(v.getId()==R.id.wechatNumber){

        }else if(v.getId()==R.id.QRcode){

        }else if(v.getId()==R.id.address){

        }else if(v.getId()==R.id.gender){

        }else if(v.getId()==R.id.region){

        }else if(v.getId()==R.id.personalizedSignature){

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}
