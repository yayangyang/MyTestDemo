package com.imooc.wetchatopensdktestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.imooc.wetchatopensdktestdemo.app.MyApplication;
import com.imooc.wetchatopensdktestdemo.utils.LogUtils;
import com.imooc.wetchatopensdktestdemo.utils.ToastUtils;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class MainActivity extends AppCompatActivity {

    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.an1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtils.e("点击了");
                wxLogin();
            }
        });
    }

    public void wxLogin() {
        LogUtils.e("wxLogin");
        if (!MyApplication.mWxApi.isWXAppInstalled()) {
            ToastUtils.showToast("您还未安装微信客户端");
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "diandi_wx_login";
        MyApplication.mWxApi.sendReq(req);
    }

}
