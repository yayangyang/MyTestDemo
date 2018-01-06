package com.yayangyang.glidetransformationstestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class MainActivity extends AppCompatActivity {

    public static final String IMG_BASE_URL = "http://images.dmzj.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView view = findViewById(R.id.iv);
        GlideUrl cookie = new GlideUrl("http://images.dmzj.com/tuijian/150_200/170707erpingmian.jpg", new LazyHeaders.Builder()
                .addHeader("Referer", IMG_BASE_URL)
                .addHeader("Accept-Encoding","gzip").build());
//        Glide.with(this).load(cookie)
//                .placeholder(R.mipmap.ic_launcher)
//                .error(R.drawable.a)
//                .transform(new GlideRoundTransform(this,6))
//                .into(view);

//        MultiTransformation multi = new MultiTransformation(
//                new RoundedCornersTransformation(20, 0));
        float density = getResources().getDisplayMetrics().density;
        Log.e("density","density"+density);
        RoundedCornersTransformation roundedCornersTransformation =
                new RoundedCornersTransformation((int) (density*6), 0);
        Glide.with(this).load(cookie)
                .apply(RequestOptions.bitmapTransform(roundedCornersTransformation))
                .into(view);

        ImageView iv02 = findViewById(R.id.iv02);
        Glide.with(this).load(cookie)
                .apply(RequestOptions.bitmapTransform(roundedCornersTransformation))
                .into(iv02);

        ImageView iv03 = findViewById(R.id.iv03);
        Glide.with(this).load(cookie)
                .apply(RequestOptions.bitmapTransform(roundedCornersTransformation))
                .into(iv03);
    }
}
