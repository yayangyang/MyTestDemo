package com.imooc.glidedemo;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.FutureTarget;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String url = "http://img1.imgtn.bdimg.com/it/u=1340624740,981264645&fm=27&gp=0.jpg";
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.image_view);
    }

    public void loadImage(View view) {
        normal();//正常加载图片
//        mSimpleTarget();//SimpleTarget加载
//        mViewTarget();//ViewTarget加载
//        yujiazai();//预加载
//        downloadOnly();//只下载,不加载
    }

    public void normal(){
        //Glide内部实现了图片压缩机制(有多少显示多少),不需要自己调整图片压缩大小
        //jpg:http://cn.bing.com/az/hprichbg/rb/MooneyFalls_ZH-CN11568488094_1920x1080.jpg
        //gif:http://p1.pstatp.com/large/166200019850062839d3
        Glide.with(this)
                .load(url)
                //.asBitmap()//都显示成静态图
                //.asGif()//都显示成动态图,但是如果图片本身是静态图会出错显示异常占位图
                .placeholder(R.drawable.a)//占位图
                .error(R.drawable.b)//异常占位图
                .diskCacheStrategy(DiskCacheStrategy.NONE)//禁用缓存
                //.override(100,100)//可指定大小(100*100像素)
                .into(imageView);
    }

    public void mSimpleTarget(){
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                imageView.setImageDrawable(resource);
            }
        };
        Glide.with(this)
                .load(url)
                .into(simpleTarget);
    }

    public void mViewTarget(){
        MyLayout myLayout = (MyLayout) findViewById(R.id.background);
        Glide.with(this)
                .load(url)
                .into(myLayout.getTarget());
    }

    public void yujiazai(){
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .preload();
        Glide.with(this)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imageView);
    }

    public void downloadOnly() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final Context context = getApplicationContext();
                    FutureTarget<File> target = Glide.with(context)
                            .load(url)
                            .downloadOnly(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL);
                    final File imageFile = target.get();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(context, imageFile.getPath(), Toast.LENGTH_LONG).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
