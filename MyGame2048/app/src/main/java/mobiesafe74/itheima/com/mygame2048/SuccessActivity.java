package mobiesafe74.itheima.com.mygame2048;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mobiesafe74.itheima.com.mygame2048.utils.SpUtil;

/**
 * Created by Administrator on 2017/5/2.
 */

public class SuccessActivity extends Activity implements View.OnClickListener{
    private TextView tv01;
    private Button jiesu;
    private Button fanhui;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success);

        initUI();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        int fenshu = extras.getInt("fenshu");
        int oldfenshu = SpUtil.getInt(this, "fenshu", -1);
        tv01.setTextSize(30);
        if(oldfenshu==-1){
            tv01.setText("新纪录:"+fenshu+"!");
        }else if(oldfenshu<fenshu){
            tv01.setText("新纪录:"+fenshu+"!");
        }else{
            tv01.setText("分数:"+fenshu+"!/n历史最高分:"+oldfenshu);
        }
    }

    private void initUI() {
        tv01= (TextView) findViewById(R.id.tv01);
        jiesu= (Button) findViewById(R.id.jiesu);
        fanhui= (Button) findViewById(R.id.fanhui);
        jiesu.setOnClickListener(this);
        fanhui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.jiesu){
            finish();
        }else if(v.getId()==R.id.fanhui){
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
