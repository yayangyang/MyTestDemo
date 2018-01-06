package mobiesafe74.itheima.com.sample;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Looper;
import android.support.v4.util.LruCache;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import static android.R.attr.path;

public class MainActivity extends AppCompatActivity {
    private TextView mTvResult;
    private ImageView mIvResult;
    //    private String mBaseUrl="http://10.2.205.149:8080/imooc_okhttp/";
    private String mBaseUrl="http://10.2.205.149:8080/";
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

        mTvResult= (TextView) findViewById(R.id.id_tv_result);
        mIvResult= (ImageView) findViewById(R.id.id_iv_result);
    }

    public void doPost(View view){
        //1.拿到okHttpClient对象
        //2.构造Request
        //2.1构造requestBody(FormBody继承自requestBody)
        FormBody formBody=new FormBody.Builder()
                .add("username","hyman")
                .add("password","doPost").build();
        Request.Builder builder=new Request.Builder();
        Request request = builder.url(mBaseUrl + "login").post(formBody).build();
        executeRequest(request);
    }

    public void doPostString(View view){
        RequestBody requestBody = RequestBody.create(MediaType.
                        parse("text/plain;chaset=utf-8"),
                "{username:hyman,password:doPostString}");
        Request.Builder builder=new Request.Builder();
        Request request = builder.url(mBaseUrl + "postString").post(requestBody).build();
        executeRequest(request);
    }

    public void doPostFile(View view){
        Lutil.e("/data/data/mobiesafe74.itheima.com.sample/");
        File file=new File("/data/data/mobiesafe74.itheima.com.sample/","cache/a.jpg");
        if(!file.exists()){
            Lutil.e(file.getAbsolutePath()+" not exist !");
            return;
        }
        //mime type
        RequestBody requestBody = RequestBody.create(MediaType.
                parse("application/octet-stream"), file);
        //不知道MediaType写什么,写上"application/octet-stream",精确则需自己百度
        Request.Builder builder=new Request.Builder();
        Request request = builder.url(mBaseUrl + "postFile").post(requestBody).build();
        executeRequest(request);
    }

    public void doUpload(View view){
//        Lutil.e("/data/data/mobiesafe74.itheima.com.sample/cache/");
        File file=new File("/data/data/mobiesafe74.itheima.com.sample/cache/","a.jpg");
        if(!file.exists()){
            Lutil.e(file.getAbsolutePath()+" not exist !");
            return;
        }
        MultipartBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", "hyman")
                .addFormDataPart("password","doUpload")
                .addFormDataPart("mPhoto","a.jpg",RequestBody.create(MediaType.
                        parse("application/octet-stream"), file))
                .build();

        CountingRequestBody countingRequestBody=new CountingRequestBody(requestBody, new
                CountingRequestBody.Listener() {
                    @Override
                    public void onRequestProgress(long byteWrited, long contentLength) {
                        Lutil.e(byteWrited+"/"+contentLength);
                    }
                });

        Request.Builder builder=new Request.Builder();
        Request request = builder.url(mBaseUrl + "uploadInfo").post(countingRequestBody).build();
        executeRequest(request);
    }

    public void doDownload(View view){
        Request.Builder builder=new Request.Builder();
        Request request = builder
                .get()
                .url(mBaseUrl+"a.mp4")
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
                File file=new File("/data/data/mobiesafe74.itheima.com.sample/cache/","abc.mp4");
                byte[] buf=new byte[128];
                FileOutputStream fos=null;
                try {
                    fos=new FileOutputStream(file);
                    while((len=is.read(buf))!=-1){
                        fos.write(buf,0,len);
                        sum+=len;
                        Lutil.e(sum+"/"+total);
                        final long finalSum=sum;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTvResult.setText(finalSum+"/"+total);
                            }
                        });
                    }
                } catch (Exception e) {
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
                Lutil.e("download success");
            }
        });
    }

    public void doDownloadImage(View view){
        Request.Builder builder=new Request.Builder();
        Request request = builder
                .get()
                .url(mBaseUrl+"tomcat.png")
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
            public void onResponse(Call call, final Response response) {
                Lutil.e("doDownload--onResponse:");
                InputStream is = response.body().byteStream();
//                BitmapFactory.Options//bitmap尺寸
                final Bitmap bitmap = BitmapFactory.decodeStream(is);//不确定尺寸需压缩
                //不确定尺寸:
                //1.加载到本地decodeFile
                //2.直接decodeStream

                //确定尺寸可写死

//                is.mark();
//                is.reset();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mIvResult.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    public void doGet(View view){
        //1.拿到okHttpClient对象
//        OkHttpClient okHttpClient=new OkHttpClient();
        //2.构造Request
        Request.Builder builder=new Request.Builder();
        Request request = builder
                .get()
                .url("https://www.baidu.com/")
//                .url(mBaseUrl+"login?username=hyman&password=doGet")
                .build();
        executeRequest(request);
    }

    private void executeRequest(Request request) {
        //3.将Request封装为Call
        Call call = mOkHttpClient.newCall(request);

        //4.执行call
//        Response response = call.execute();//同步

        call.enqueue(new Callback() {//异步
            @Override
            public void onFailure(Call call, IOException e) {
                Lutil.e("onFailure:"+e.getMessage());
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String res = response.body().string();
                Lutil.e("onResponse:"+res);
                boolean b = Looper.getMainLooper() == Looper.myLooper();
                Lutil.e(""+b);
//                InputStream is = response.body().byteStream();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvResult.setText(res);
                    }
                });
            }
        });
    }
}
