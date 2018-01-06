package com.yayangyang.lunbotestdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static String IMG_BASE_URL="http://images.dmzj.com/";

    private ArrayList<View> mViewArrayList=new ArrayList<>();
    private ArrayList<String> mStrArrayList=new ArrayList<>();

    private ViewPager mViewPager;

    private Handler mHandler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if(mViewPager.getCurrentItem()<mStrArrayList.size()-1){
                mViewPager.setCurrentItem(mViewPager.getCurrentItem()+1);
            }else{
                mViewPager.setCurrentItem(0);
            }
            mHandler.sendEmptyMessageDelayed(0,1000);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        configViews();
    }

    private void initData() {
        mStrArrayList.add("http://images.dmzj.com/tuijian/750_480/171208manzhantj2.jpg");
        mStrArrayList.add("http://images.dmzj.com/tuijian/750_480/171206youxitj1.jpg");
        mStrArrayList.add("http://images.dmzj.com/tuijian/750_480/171207benghuaitj1.jpg");
        mStrArrayList.add("http://images.dmzj.com/tuijian/750_480/1204kongxiang01.jpg");
        mStrArrayList.add("http://images.dmzj.com/tuijian/750_480/171211rujiantj2.jpg");
        for(int i=0;i<5;i++){
            mViewArrayList.add(View.inflate(this,R.layout.item_main,null));
        }
    }

    private void configViews() {
        mViewPager = findViewById(R.id.viewpager);
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return mStrArrayList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViewArrayList.get(position);
                ImageView imageView = view.findViewById(R.id.iv);
                GlideUrl cookie = new GlideUrl(mStrArrayList.get(position), new LazyHeaders.Builder()
                        .addHeader("Referer", IMG_BASE_URL)
                        .addHeader("Accept-Encoding","gzip").build());
                GlideApp.with(MainActivity.this)
                        .load(cookie)
                        .into(imageView);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        mHandler.sendEmptyMessageDelayed(0,1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
