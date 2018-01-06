package mobiesafe74.itheima.com.immoc_recorder.Utils;

import android.util.Log;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/5/14.
 */

public class okhttpUtil {
    private static CookieJar cookieJar=new CookieJar() {
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
    private static OkHttpClient mOkHttpClient=new OkHttpClient().newBuilder().cookieJar(cookieJar).build();
    public static void doDownload(final String filePath){
//        final URL url=null;
//        try {
//            new URL(filePath);
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        }
        Request.Builder builder=new Request.Builder();
        Request request = builder
                .get()
                .url(filePath)
//                .url(mBaseUrl+"files/a.jpg")
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Lutil.e("doDownload--onFailure:"+e.getMessage());
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, final Response response){
                Lutil.e("doDownload--onResponse:");
                final long total = response.body().contentLength();
                long sum=0L;
                InputStream is=response.body().byteStream();
                int len=0;
                Log.e("cache",UIUtils.getContext().getCacheDir()+"");
                File file=new File(UIUtils.getContext().getCacheDir()+UUID.randomUUID().toString()+".amr");
                byte[] buf=new byte[128];
                FileOutputStream fos=null;
//                Log.e("搜索","sss");
                try {
                    fos=new FileOutputStream(file);
                    while((len=is.read(buf))!=-1){
                        fos.write(buf,0,len);
                        sum+=len;
                        Lutil.e(sum+"/"+total);
                    }
                    Lutil.e("download success");
                } catch (Exception e) {
                    Lutil.e("download failed");
                    e.printStackTrace();
                }finally {
                    try {
                        //细致一点需要分别try catch
                        fos.flush();
                        fos.close();
                        is.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}
