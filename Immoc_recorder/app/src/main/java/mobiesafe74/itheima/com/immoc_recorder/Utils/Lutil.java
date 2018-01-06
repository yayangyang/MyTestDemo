package mobiesafe74.itheima.com.immoc_recorder.Utils;

import android.util.Log;

/**
 * Created by Administrator on 2017/4/23.
 */

public class Lutil {
    private static  final String TAG="Imooc_okhttp";
    private static boolean debug=true;

    public static void e(String msg){
        if(debug){
            Log.e(TAG,msg);
        }
    }
}
