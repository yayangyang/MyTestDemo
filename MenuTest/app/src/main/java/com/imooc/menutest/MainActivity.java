package com.imooc.menutest;

import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        LogUtils.e("onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        LogUtils.e("onOptionsItemSelected");
        int id = item.getItemId();
        if(id==R.id.action_search){
            Toast.makeText(this,"点击了action_search",Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        LogUtils.e("onPrepareOptionsPanel");
        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //开启时在onCreateOptionsMenu之后调用,弹出菜单时会调用1次
        LogUtils.e("onPrepareOptionsMenu");
        return super.onPrepareOptionsMenu(menu);
    }
}
