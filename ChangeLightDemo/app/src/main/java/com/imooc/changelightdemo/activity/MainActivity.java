package com.imooc.changelightdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import com.imooc.changelightdemo.R;
import com.imooc.changelightdemo.app.MyApp;
import com.imooc.changelightdemo.utils.AppUtils;
import com.imooc.changelightdemo.utils.ScreenUtils;

import static android.R.attr.id;
import static android.R.attr.permission;

public class MainActivity extends AppCompatActivity {
    private SeekBar mSeekBar;
    private CheckBox mCheckBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        request();
        mSeekBar= (SeekBar) findViewById(R.id.seekbar);
        mCheckBox= (CheckBox) findViewById(R.id.checkbox);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if(fromUser){
                    Log.e("yyy","eeeeee");
                    ScreenUtils.saveScreenBrightnessInt100(progress, MainActivity.this);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.e("isChecked",""+isChecked);
                if(isChecked){
                    ScreenUtils.startAutoBrightness(MainActivity.this);
                }else{
                    ScreenUtils.stopAutoBrightness(MainActivity.this);
                }
            }
        });
        getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.SCREEN_BRIGHTNESS), true, Brightness);
    }

    private void request() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (!Settings.System.canWrite(MainActivity.this)) {
                Intent intent = new Intent(android.provider.Settings.ACTION_MANAGE_WRITE_SETTINGS);
                intent.setData(Uri.parse("package:" + MainActivity.this.getPackageName()));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                MainActivity.this.startActivity(intent);
            }
        }
    }
    /**
     * 时刻监听系统亮度改变事件
     */
    private ContentObserver Brightness = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            if (!ScreenUtils.isAutoBrightness(MainActivity.this)) {
                Log.e("onChange","onChange");
                mSeekBar.setProgress(ScreenUtils.getScreenBrightness());
            }
        }
    };
}
