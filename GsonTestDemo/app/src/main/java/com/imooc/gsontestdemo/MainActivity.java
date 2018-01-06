package com.imooc.gsontestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//可看出try..catch不捕获异常接下来代码仍可以运行
public class MainActivity extends AppCompatActivity {

    private String mBaseUrl="http://api.zhuishushenqi.com/book/recommend?gender=male";
    private CookieJar cookieJar=new CookieJar() {
        private Map<String, List<Cookie>> cookieStore = new HashMap<>();
        @Override
        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
            cookieStore.put(url.host(), cookies);
        }

        @Override
        public List<Cookie> loadForRequest(HttpUrl url) {
            List<Cookie> cookies = cookieStore.get(url.host());
            return cookies != null ? cookies : new ArrayList<Cookie>();
        }
    };
    private OkHttpClient mOkHttpClient=new OkHttpClient().newBuilder().cookieJar(cookieJar).build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        doDownload();
    }

    public void doDownload(){
        Request.Builder builder=new Request.Builder();
        final Request request = builder
                .get()
                .url(mBaseUrl)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e("doDownload--onFailure:"+e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                LogUtils.e("doDownload--onResponse:");

                String string = response.body().string();
                Recommend recommend=null;
                try {
                    recommend = new Gson().fromJson(string+"wwwwww", Recommend.class);
                }catch (Exception e){
                    LogUtils.e("错误");
                    e.printStackTrace();
                }

                if(recommend!=null){
                    LogUtils.e(recommend.ok+"");
                }else{
                    LogUtils.e("gson转换失败");
                }
            }
        });
    }

}
