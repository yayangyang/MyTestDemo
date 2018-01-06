package com.yayangyang.remoteviewstestdemo;

import android.app.PendingIntent;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.MessageQueue;
import android.os.SystemClock;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.concurrent.CopyOnWriteArrayList;

public class MyAppWidgetProvider extends AppWidgetProvider {

    public static final String TAG="MyAppWidgetProvider";
    public static final String CLICK_ACTION="com.yayangyang.remoteviewstestdemo.action.CLICK";

    public MyAppWidgetProvider() {
        super();
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        Log.e("onReceive-action:",intent.getAction());
        super.onReceive(context, intent);

        if(intent.getAction().equals(CLICK_ACTION)){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap srcBitmap= BitmapFactory.decodeResource(context.getResources(),R.drawable.bitch_09);
                    AppWidgetManager appWidgetManager=AppWidgetManager.getInstance(context);
                    for(int i=0;i<37;i++){
                        float degree=(i*10)%360;
                        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget);
                        remoteViews.setImageViewBitmap(R.id.iv,rotateBitmap(context,srcBitmap,degree));
                        Intent intentClick=new Intent();
                        intentClick.setAction(CLICK_ACTION);
                        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intentClick,0);
                        remoteViews.setOnClickPendingIntent(R.id.iv,pendingIntent);
                        Log.e("www","ww1");
                        appWidgetManager.updateAppWidget(new ComponentName(context,MyAppWidgetProvider.class),remoteViews);
                        Log.e("www","ww2");
                        SystemClock.sleep(30);
                    }
                }
            }).start();
        }
    }

    @Override

    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        Log.e("ww","onUpdate");

        final int counter=appWidgetIds.length;
        Log.e("ww",counter+"");
        for(int i=0;i<counter;i++){
            int appWidgetId=appWidgetIds[i];
            onWidgetUpdate(context,appWidgetManager,appWidgetId);
        }
    }

    public void onWidgetUpdate(Context context,AppWidgetManager appWidgetManager,int appWidgetId){
        Log.e("appWidgetId",appWidgetId+"");
        RemoteViews remoteViews=new RemoteViews(context.getPackageName(),R.layout.widget);

        Intent intentClick=new Intent();
        intentClick.setAction(CLICK_ACTION);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(context,0,intentClick,0);
        remoteViews.setOnClickPendingIntent(R.id.iv,pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetId,remoteViews);
    }

    public Bitmap rotateBitmap(Context context,Bitmap srcBitmap,float degree){
        Matrix matrix=new Matrix();
        matrix.reset();
        matrix.setRotate(degree);
        return Bitmap.createBitmap(srcBitmap,0,0,
                srcBitmap.getWidth(),srcBitmap.getHeight(),matrix,true);
    }

    @Override
    public void onEnabled(Context context) {
        Log.e("ww","onEnabled");
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        Log.e("ww","onDeleted");
    }

    @Override
    public void onDisabled(Context context) {
        Log.e("ww","onDisabled");
    }

}
