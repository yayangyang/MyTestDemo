package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import mobiesafe74.itheima.com.immoc_recorder.R;

/**
 * Created by Administrator on 2017/5/20.
 */

public class addNewFriendActivity extends BaseActivity {
    private EditText addNewFriend;
    private Toolbar mToolbar;
    private Boolean canVisible;
    private View view_01;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewfriend);

        init();
    }

    private void init() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        canVisible = bundle.getBoolean("canVisible");
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        if(canVisible){
            mToolbar.setTitle("新的朋友");//设置标题要写在setSupportActionBar(mToolbar);之前才有效
        }else{
            mToolbar.setTitle("添加朋友");
        }
//        addNewFriend = (EditText) findViewById(R.id.addNewFriend);
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationIcon(R.drawable.personal_return_btn);
        view_01= findViewById(R.id.view_01);
        view_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.e("ll_01","点击了");
                Intent intent = new Intent();
                intent.setClass(addNewFriendActivity.this,searchActivity.class);
                startActivity(intent);
            }
        });
//        Log.e("canVisible",canVisible+"");
        if(canVisible){
            PersonalDataActivity.setCloseAddNewFriendActivityListener(new PersonalDataActivity.CloseAddNewFriendActivityListener() {
                @Override
                public void closeAddNewFriendActivityListener() {
                    finish();
                }
            });
        }else{
            PersonalDataActivity.setCloseSecondAddNewFriendActivityListener(new PersonalDataActivity.CloseAddNewFriendActivityListener() {
                @Override
                public void closeAddNewFriendActivityListener() {
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(canVisible) getMenuInflater().inflate(R.menu.addfriend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }else if(item.getItemId()==R.id.addFriend){
            Intent intent = new Intent();
            intent.setClass(addNewFriendActivity.this,addNewFriendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putBoolean("canVisible",false);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //防止内存泄漏
        if(PersonalDataActivity.mCloseSecondAddNewFriendActivityListener==null){
            PersonalDataActivity.mCloseAddNewFriendActivityListener=null;
        }
        PersonalDataActivity.mCloseSecondAddNewFriendActivityListener=null;
    }
}
