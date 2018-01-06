package mobiesafe74.itheima.com.immoc_recorder.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;

import java.text.SimpleDateFormat;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;

import static mobiesafe74.itheima.com.immoc_recorder.R.id.edit;
import static mobiesafe74.itheima.com.immoc_recorder.Utils.MyDatabaseUtil.isFriend;
import static mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity.db;

/**
 * Created by Administrator on 2017/5/20.
 */

public class searchActivity extends BaseActivity {
    private Toolbar mToolbar;
    private ImageView mImageView;
    private EditText mEditText;
    private TextView textview;
    private LinearLayout ll_01,ll_02;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initUI();
    }

    private void initUI() {
        ll_01= (LinearLayout) findViewById(R.id.ll_01);
        ll_01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查询是否有该用户,通过用户名(类似微信号)查询,若成功跳转到查询到的用户详情页
                AVQuery query = new AVQuery("_User");
                final AVQuery<AVObject> avObjectAVQuery = query.whereEqualTo("username", mEditText.getText().toString());
//                Log.e("username",mEditText.getText().toString());
                avObjectAVQuery.findInBackground(new FindCallback<AVObject>() {
                    @Override
                    public void done(List<AVObject> list, AVException e) {
                        if(e==null&&list.size()>0){//不清楚查询不到结果时e仍为null,加入集合的大小来规避错误
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            AVObject avObject = list.get(0);
                            String username = (String) avObject.get("username");
                            String createdAt = formatter.format(avObject.get("createdAt"));
                            String updatedAt = formatter.format(avObject.get("updatedAt"));
                            //查询是否有该好友
                            boolean isFriend = isFriend(db, username);//这里的username是所查找用户的唯一标识(类似微信号)
                            Intent intent = new Intent();
                            Bundle bundle = new Bundle();
                            bundle.putString("username",username);
                            bundle.putString("createdAt",createdAt);
                            bundle.putString("updatedAt",updatedAt);
                            bundle.putBoolean("isFriend",isFriend);
                            intent.putExtras(bundle);
                            intent.setClass(searchActivity.this,PersonalDataActivity.class);
                            startActivity(intent);
                            finish();
                        }else if(list.size()<=0&&e==null){
                            Log.e("错误","e仍为空");
                            //查询不到
                            ll_01.setVisibility(View.GONE);
                            ll_02.setVisibility(View.VISIBLE);
                        }else{
                            Log.e("错误",e.getMessage());
                        }
                    }
                });
            }
        });
        ll_02= (LinearLayout) findViewById(R.id.ll_02);
        ll_02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mImageView= (ImageView) findViewById(R.id.imageview);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        textview= (TextView) findViewById(R.id.textview);
        mEditText= (EditText) findViewById(edit);
        Drawable drawable = getResources().getDrawable(R.drawable.actionbar_search_icon,null);
        drawable.setBounds(0, 0, 100, 100);//第一0是距左边距离，第二0是距上边距离，40分别是长宽
        mEditText.setCompoundDrawables(drawable, null, null, null);//只放左边
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if(mEditText.getText().length()>0){
                    ll_01.setVisibility(View.VISIBLE);
                }else{
                    ll_01.setVisibility(View.GONE);
                }
                ll_02.setVisibility(View.GONE);
                textview.setText(mEditText.getText().toString());
            }
        });

        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }

}
