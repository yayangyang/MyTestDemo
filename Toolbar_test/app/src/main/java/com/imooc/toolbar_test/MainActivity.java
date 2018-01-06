package com.imooc.toolbar_test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import static com.imooc.toolbar_test.R.drawable.a;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mToolbar= (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("测试");
        mToolbar.setNavigationIcon(R.drawable.a);
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ww1","onClick");
            }
        });
        mToolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("ww2","onClick");
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
