package com.yayangyang.imagecompresstestdemo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

public class MainActivity extends AppCompatActivity {

    public static String IMG_BASE_URL="http://images.dmzj.com/";
    private String imageUrl="http://images.dmzj.com/tuijian/750_480/171208manzhantj2.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DisplayMetrics dm =getResources().getDisplayMetrics();
        int w_screen = dm.widthPixels;
        int h_screen = dm.heightPixels;
        Log.e("w_screen","w_screen"+w_screen);
        Log.e("h_screen","h_screen"+h_screen);
        ImageView imageView = findViewById(R.id.image);

        //适度缩放能节省内存,缩放值超过一定值虽然比没缩放内存少但是会增加一些内存
        //直接解析资源文件与Glide比较起来更省内存
//        Bitmap bitmap = ImageUtil.getimage
//                (getResources(), R.drawable.lunbo, w_screen, (int) dpToPx(200));
//        imageView.setImageDrawable(new BitmapDrawable(getResources(),bitmap));

        //Glide默认应该已经做了二次采样
//        GlideUrl cookie = new GlideUrl(imageUrl, new LazyHeaders.Builder()
//                .addHeader("Referer", IMG_BASE_URL)
//                .addHeader("Accept-Encoding","gzip").build());
//        GlideApp.with(this).load(cookie).override(w_screen, (int) dpToPx(200))
//                .into(imageView);
        DeviceUtils.printDisplayInfo(this);
    }

    public float dpToPx(float dp) {
        return dp * getResources().getDisplayMetrics().density;
    }
}
