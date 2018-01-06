package mobiesafe74.itheima.com.webview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {
    private WebView mWebView;
    private ProgressBar pb_loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mWebView= (WebView) findViewById(R.id.webview);
        pb_loading= (ProgressBar) findViewById(R.id.pb_loading);
        use();
    }

    private void use() {
        mWebView.loadUrl("http://www.itheima.com");

        WebSettings settings = mWebView.getSettings();
        settings.setBuiltInZoomControls(true);//显示缩放按钮(wap网页不支持)
        settings.setUseWideViewPort(true);//支持双击缩放(wap网页不支持)
        settings.setJavaScriptEnabled(true);//支持js功能

        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                System.out.println("开始加载网页了");
                pb_loading.setVisibility(View.VISIBLE);
            }
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                System.out.println("网页加载结束了");
                pb_loading.setVisibility(View.INVISIBLE);
            }
            //所有链接跳转会走此方法
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                System.out.println("跳转链接"+url);
                view.loadUrl(url);//在跳转链接时强制在当前webview中加载
                return true;
            }
        });
        //mWebView.goBack();//调到上个页面
        //mWebView.goForward();//调到下个页面

        mWebView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                //进度发生变化
                System.out.println("进度:"+newProgress);
            }
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //网页标题
                System.out.println("网页标题:"+title);
            }
        });
    }
}
