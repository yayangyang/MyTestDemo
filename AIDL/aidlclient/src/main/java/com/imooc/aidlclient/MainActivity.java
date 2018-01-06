package com.imooc.aidlclient;

import android.content.ComponentName;
import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.os.Binder;
import android.os.IBinder;
import android.os.Looper;
import android.os.Messenger;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


import com.imooc.aidl.IImoocAidl;
import com.imooc.aidl.Person;
import com.imooc.aidl.zidingyi;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mEtNum1;
    private EditText mEtNum2;
    private EditText mEtRes;

    private Button mBtnAdd,btn_zidingyi;

    IImoocAidl mImoocAidl;
    zidingyi mZidingyi;

    private ServiceConnection conn=new ServiceConnection() {

        //绑定上服务的时候
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //拿到了远程服务
//            mImoocAidl=IImoocAidl.Stub.asInterface(service);
            mZidingyi=zidingyi.Stub.asInterface(service);
            Log.e("mZidingyi",mZidingyi+"");
            try {
                //设置死亡代理,binder断裂时重连
                service.linkToDeath(mDeathRecipient,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            Log.e("绑定服务","ww");
        }

        //断开服务的时候
        @Override
        public void onServiceDisconnected(ComponentName name) {
            //回收资源
//            mImoocAidl=null;
//            mZidingyi=null;
            //service所属app重新安装客户端会断开与service服务
            Log.e("断开服务","ww");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        //软件一启动就绑定服务
        bindService();
    }

    private void initView() {
        mEtNum1= (EditText) findViewById(R.id.et_num1);
        mEtNum2= (EditText) findViewById(R.id.et_num2);
        mEtRes= (EditText) findViewById(R.id.et_res);

        mBtnAdd= (Button) findViewById(R.id.btn_add);
        btn_zidingyi= (Button) findViewById(R.id.btn_zidingyi);

        mBtnAdd.setOnClickListener(this);
        btn_zidingyi.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_add){
            int num1=Integer.parseInt(mEtNum1.getText().toString());
            int num2=Integer.parseInt(mEtNum2.getText().toString());
            try {
                int res = mImoocAidl.add(num1, num2);
                mEtRes.setText(res+"");
            } catch (RemoteException e) {
                e.printStackTrace();
                mEtRes.setText("错误了");
            }
        }else if(v.getId()==R.id.btn_zidingyi){
            try {
                Log.e("点击绑定","btn_zidingyi");
                List<Person> persons=null;
                //当Binder断裂当对象没置空时使用Binder方法不会报错(即无反应,无效果)
                if(mZidingyi!=null&&mZidingyi.asBinder().isBinderAlive()){
                    persons = mZidingyi.add(new Person("abc", 21));
                }
//                if(mZidingyi!=null&&mZidingyi.asBinder().isBinderAlive()){
//
//                }
                Log.e("Persons",persons.toString());
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindService() {
        //获取到服务端
        Intent intent=new Intent();
        //5.0新版本必须显示Intent启动绑定服务
//        intent.setComponent(new ComponentName("com.imooc.aidl","com.imooc.aidl.IRemoveService"));
        intent.setComponent(new ComponentName("com.imooc.aidl","com.imooc.aidl.zidingyiService"));
        Log.e("ww","阿诗丹顿所");
        bindService(intent,conn, Context.BIND_AUTO_CREATE);
        Log.e("ww","阿诗丹顿所2");
    }

    private IBinder.DeathRecipient mDeathRecipient=new IBinder.DeathRecipient() {
        @Override
        public void binderDied() {
            Log.e("binderDied-mZidingyi:",mZidingyi+"");
            boolean b = Looper.getMainLooper() == Looper.myLooper();
            Log.e("是否在主线程:",b+"");
            if(mZidingyi==null){
                return;
            }
            mZidingyi.asBinder().unlinkToDeath(mDeathRecipient,0);
            mZidingyi=null;
            bindService();
        }
    };

    @Override
    protected void onDestroy() {
        Log.e("MainActivity","onDestroy");
        super.onDestroy();
        unbindService(conn);
    }
}
