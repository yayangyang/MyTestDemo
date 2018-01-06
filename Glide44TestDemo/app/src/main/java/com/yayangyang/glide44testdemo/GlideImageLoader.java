package com.yayangyang.glide44testdemo;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestOptions;
import com.youth.banner.loader.ImageLoader;

import java.lang.ref.WeakReference;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class GlideImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Log.e("path","path:"+path);
//        Glide.with(context).load(R.drawable.a).into(imageView);

        GlideUrl cookie = new GlideUrl((String)path, new LazyHeaders.Builder()
                .addHeader("Referer", "http://images.dmzj.com/")
                .addHeader("Accept-Encoding","gzip").build());
        RequestOptions requestOptions = new RequestOptions();
//        RoundedCornersTransformation roundedCornersTransformation =
//                new RoundedCornersTransformation(20, 0);
        Glide.with(context).load(cookie)
                .apply(requestOptions)
                .into(imageView);
    }
}