package mobiesafe74.itheima.com.immoc_recorder.manager;

import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/5/1.
 */

public class VoiceManager {
    public static int position=-1;
    public static View mAnimView;

    public static View getmAnimView() {
        return mAnimView;
    }

    public static void setmAnimView(View mAnimView) {
        VoiceManager.mAnimView = mAnimView;
    }

    public static int getPosition() {
        return position;
    }

    public static void setPosition(int p) {
        position = p;
    }

    public static View getAnimView() {
        return mAnimView;
    }

    public static void setAnimView(View animView) {
        Log.e("ww","mAnimView更改了!!!!!!!!!!!!!!!!!!!!!!!! ");
        mAnimView = animView;
    }

}
