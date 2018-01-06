package com.yayangyang.listennetworkstatechangetestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements NetworkChangeReceiver.OnNetWorkChangeListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NetworkChangeReceiver.getInstance().setOnNetWorkChange(this);
    }

    @Override
    public void onChange(String stateInfo) {
        TextView tv = findViewById(R.id.tv);
        tv.setText(stateInfo);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NetworkChangeReceiver.getInstance().delOnNetWorkChange(this);
    }
}
