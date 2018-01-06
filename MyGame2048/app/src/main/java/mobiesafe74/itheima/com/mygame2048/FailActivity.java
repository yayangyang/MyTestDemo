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

public class FailActivity extends Activity implements View.OnClickListener{
    private Button jiesu;
    private Button fanhui;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fail);

        initUI();
    }

    private void initUI() {
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
