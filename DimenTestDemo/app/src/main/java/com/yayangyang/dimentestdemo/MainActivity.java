package com.yayangyang.dimentestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int px = getResources().getDimensionPixelOffset(R.dimen.ww);
        Toast.makeText(this,px+"",Toast.LENGTH_LONG).show();

        DeviceUtils.printDisplayInfo(this);
    }
}
