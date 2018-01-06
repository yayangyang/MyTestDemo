package mobiesafe74.itheima.com.mygame2048.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SpUtil {
	private static SharedPreferences sp;

	public static void putBoolean(Context ctx,String key,boolean value){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putBoolean(key, value).commit();
	}
	public static Boolean getBoolean(Context ctx, String key, boolean defValue){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getBoolean(key, defValue);
	}
	
	public static void putString(Context ctx,String key,String value){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putString(key, value).commit();
	}
	public static String getString(Context ctx,String key,String defValue){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getString(key, defValue);
	}
	public static void putInt(Context ctx,String key,int value){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putInt(key, value).commit();
	}
	public static int getInt(Context ctx,String key,int defValue){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getInt(key, defValue);
	}
	public static void putFloat(Context ctx,String key,float value){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().putFloat(key, value).commit();
	}
	public static float getFloat(Context ctx,String key,float defValue){
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		return sp.getFloat(key, defValue);
	}
	public static void remove(Context ctx, String key) {
		if(sp==null){
			sp = ctx.getSharedPreferences("config", Context.MODE_PRIVATE);
		}
		sp.edit().remove(key).commit();
	}
}
