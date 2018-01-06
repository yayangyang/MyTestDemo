package com.yayangyang.findviewbyidtestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tv01 = findViewById(R.id.tv01);
        TextView tv02 = findViewById(R.id.tv01);
        tv01.setText("ww");
        tv02.setText("33");
        tv01.setText("qq");
    }
}
