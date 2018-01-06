package com.imooc.notificationdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import static android.R.attr.id;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private NotificationManager mNotificationManager;
    private int notifiId=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_send_notification).setOnClickListener(this);
        findViewById(R.id.btn_cancel_notification).setOnClickListener(this);
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_send_notification){
            sendNotificaction();
        }else if(v.getId()==R.id.btn_cancel_notification){
            mNotificationManager.cancel(notifiId);
        }
    }

    private void sendNotificaction() {
        //实例化一个Notification对象
//        int icon=R.drawable.icon_notification;
//        CharSequence tickerText="当前正在播放:超级英雄";
//        long when=System.currentTimeMillis();
//        Notification notification=new Notification(icon,tickerText,when);
//        notification.flags=Notification.FLAG_ONGOING_EVENT;//设置不可划掉

        //设置通知要显示的内容
        //保留setTicker到setWhen可兼容2.3以前版本(使之不报错,布局采用默认布局)
        final NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.icon_notification)//提示icon(没有设置自定义通知布局时图片显示不出)
                .setTicker("当前正在播放:超级英雄")//提示文本
                .setOngoing(true)// 设置通知无法划掉
                .setContentTitle("超级英雄")//设置通知标题
                .setContentText("邓超")//设置通知内容
                .setContentIntent(getPendingIntent(WHAT_ROOT))
                .setWhen(System.currentTimeMillis())//通知时间
                .setContent(getRemoteView());//设置自定义通知的布局
        //发送通知
        //参数1:如果id相同,则第二次不能发送
        mNotificationManager.notify(notifiId,builder.build());
    }

    public static final int WHAT_PRE=1;
    public static final int WHAT_NEXT=2;
    public static final int WHAT_ROOT=3;

    private RemoteViews getRemoteView() {
        RemoteViews remoteViews=new RemoteViews(getPackageName(),R.layout.notification);
        remoteViews.setTextViewText(R.id.tv_title,"朋友的酒");
        remoteViews.setTextViewText(R.id.tv_artist,"李晓杰");
        remoteViews.setOnClickPendingIntent(R.id.btn_pre,getPendingIntent(WHAT_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_next,getPendingIntent(WHAT_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.ll_root,getPendingIntent(WHAT_ROOT));
        return remoteViews;
    }

    private PendingIntent getPendingIntent(int what) {
        int requestCode=what;//用于判断获取的PendingIntent是否是同一个(同一个不会重复创建PendingIntent对象)
        Intent intent = new Intent(this,DemoActivity.class);
        intent.putExtra("what",what);
        //PendingIntent.FLAG_UPDATE_CURRENT表示更新intent中的数据
        return PendingIntent
                .getActivity(this,requestCode,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
