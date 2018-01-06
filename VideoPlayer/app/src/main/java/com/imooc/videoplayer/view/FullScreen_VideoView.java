package com.imooc.videoplayer.view;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;
import android.widget.VideoView;

/**
 * Created by Administrator on 2017/6/11.
 */

public class FullScreen_VideoView extends VideoView {
    private boolean fullscreen=false;
    private MediaPlayer.OnBufferingUpdateListener mBufferingUpdateListener;

//    public void setBufferingUpdateListener(MediaPlayer.OnBufferingUpdateListener bufferingUpdateListener) {
//        mBufferingUpdateListener = bufferingUpdateListener;
//        if(mBufferingUpdateListener!=null){
//            Log.e("|w","setBufferingUpdateListener");
//        }
//    }

    public boolean isFullscreen() {
        return fullscreen;
    }

    public FullScreen_VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FullScreen_VideoView(Context context) {
        super(context);
    }

    public void changeScreenSize() {
        if(!fullscreen){//设置RelativeLayout的全屏模式
            Log.e("fullscreen","fullscreen");
            RelativeLayout.LayoutParams layoutParams=
                    new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.FILL_PARENT, RelativeLayout.LayoutParams.FILL_PARENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            setLayoutParams(layoutParams);

            fullscreen = true;//改变全屏/窗口的标记
        }else{//设置RelativeLayout的窗口模式
            RelativeLayout.LayoutParams lp=new RelativeLayout.LayoutParams(300,400);
            lp.addRule(RelativeLayout.CENTER_IN_PARENT);
            setLayoutParams(lp);
            fullscreen = false;//改变全屏/窗口的标记
        }
    }

}
