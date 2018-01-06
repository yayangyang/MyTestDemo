package com.imooc.butterknifedemo;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindArray;
import butterknife.BindColor;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.tv01)
    TextView mTv01;
    @BindView(R.id.bt01)
    Button mBt01;
    @BindView(R.id.bt02)
    Button mBt02;

    @BindString(R.string.app_name)  //绑定资源文件中string字符串
            String str;
    @BindArray(R.array.city)  //绑定string里面array数组
            String [] citys ;
    @BindColor( R.color.colorAccent ) //具体色值在color文件中
            int black ;  //绑定一个颜色值

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(mBt02==null){
            Log.e("mBt02","为空");
        }else{
            Log.e("mBt02","不为空");
        }
    }

    @OnClick({R.id.tv01, R.id.bt01})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv01:
                Log.e("点击了","tv01");
                break;
            case R.id.bt01:
                Log.e("点击了","bt01");
                break;
        }
    }
}
