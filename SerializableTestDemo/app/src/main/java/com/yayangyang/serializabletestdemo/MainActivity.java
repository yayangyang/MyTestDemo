package com.yayangyang.serializabletestdemo;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);

        Bean bean = SharedPreferencesUtil.getInstance().getObject("bean", Bean.class);
        if(bean== null){
            Log.e("bean","为空");
            SharedPreferencesUtil.getInstance().putObject("bean",new Bean());
        }else{
            Log.e("bean","ww"+bean);
        }
    }
}
