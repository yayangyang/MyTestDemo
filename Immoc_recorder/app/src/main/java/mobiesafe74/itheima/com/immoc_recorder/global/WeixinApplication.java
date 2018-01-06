package mobiesafe74.itheima.com.immoc_recorder.global;

import android.app.Application;
import android.content.Context;
import android.os.Handler;

/**
 *	自定义application,进行全局初始化
 */
public class WeixinApplication extends Application {
	private static Context Context;
	private static Handler Handler;
	private static int mainThreadTid;

	@Override
	public void onCreate() {
		super.onCreate();
		
		Context = getApplicationContext();
		Handler = new Handler();
		mainThreadTid = android.os.Process.myTid();
	}

	public static Context getContext() {
		return Context;
	}

	public static Handler getHandler() {
		return Handler;
	}

	public static int getMainThreadTid() {
		return mainThreadTid;
	}
}
