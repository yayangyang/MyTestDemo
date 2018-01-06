package com.yayangyang.imageviewtestdemo;

import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

//直接设置图片默认使用原图片的宽高创建bitmap
//放在不同drawable文件夹会根据dpi放大缩小原图片的宽高再创建位图
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = findViewById(R.id.image);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        int width = drawable.getBitmap().getWidth();
        Toast.makeText(this,"wwwwwwwwwwwwwwwwwwwwwww"+width,Toast.LENGTH_LONG).show();

        DeviceUtils.printDisplayInfo(this);
    }
}
