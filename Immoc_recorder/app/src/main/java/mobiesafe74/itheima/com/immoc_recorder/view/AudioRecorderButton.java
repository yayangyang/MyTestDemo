package mobiesafe74.itheima.com.immoc_recorder.view;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.Lutil;
import mobiesafe74.itheima.com.immoc_recorder.Utils.SpUtil;
import mobiesafe74.itheima.com.immoc_recorder.activity.ChatActivity;
import mobiesafe74.itheima.com.immoc_recorder.manager.AudioManager;
import mobiesafe74.itheima.com.immoc_recorder.manager.DialogManager;

/**
 * Created by Administrator on 2017/4/28.
 */

public class AudioRecorderButton extends AppCompatButton implements AudioManager.AudioStateListener {

    private static final int DISTANCE_Y_CANCEL=50;
    public static final int STATE_NORMAL=1;
    private static final int STATE_RECORDING=2;
    private static final int STATE_WANT_TO_CANCEL=3;
    private static final String IS_FIRST_SOUND_RECORDING = "is_first_sound_recording";

    public static int mCurrentState=STATE_NORMAL;
    boolean isRecording=false;//是否已经开始录音

    private DialogManager mDialogManager;
    private AudioManager mAudioManager;

    private ChatActivity mActivity;
    public void setActivity(Activity activity) {
        mActivity = (ChatActivity) activity;
    }

    private float mTime=0;
    private boolean mReady;//是否触发longclick

    private static final int MSG_AUDIO_PREPARED=0x110;
    private static final int MSG_VOICE_CHANGED=0x111;
    private static final int MSG_DIALOG_DISMISS=0x112;
    private static final int NOT_FIRST_SOUND_RECORDING=0x113;

    public AudioRecorderButton(Context context) {
        this(context,null);
    }

    //录音完成后的回调
    public interface AudioFinishRecorderListener{
        void onFinish(float seconds,String filePath);
    }

    private AudioFinishRecorderListener mListener;

    public void setAudioFinishRecorderListener(AudioFinishRecorderListener listener){
        mListener=listener;
    }

    //获取音量大小的Runnable
    private Runnable mGetVoiceLevelRunnable=new Runnable() {
        @Override
        public void run() {
            while(isRecording){
                try {
                    Thread.sleep(100);
                    mTime+=0.1f;
                    mHandler.sendEmptyMessage(MSG_VOICE_CHANGED);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==MSG_AUDIO_PREPARED){
                Log.e("ww","MSG_AUDIO_PREPARED");
                Boolean aBoolean = SpUtil.getBoolean(getContext(), IS_FIRST_SOUND_RECORDING, true);
                Log.e("aBoolean",aBoolean+"发发发否");
                if(!aBoolean){
                    mDialogManager.showRecordingDialog();//显示在audio end prepared以后
                    isRecording=true;
                    new Thread(mGetVoiceLevelRunnable).start();
                }
//                if(aBoolean){
//                    mHandler.sendEmptyMessageDelayed(NOT_FIRST_SOUND_RECORDING,1000);
//                }
            }else if(msg.what==MSG_VOICE_CHANGED){
                mDialogManager.updateVoiceLevel(mAudioManager.getVoiceLevel(7));
            }else if(msg.what==MSG_DIALOG_DISMISS){
                mDialogManager.dimissDialog();
            }
//            else if(msg.what==NOT_FIRST_SOUND_RECORDING){
//                SpUtil.putBoolean(getContext(),IS_FIRST_SOUND_RECORDING,false);
////                Log.e("设为false","false");
//            }
        }
    };

    @Override
    public void wellPrepared() {
        Toast.makeText(getContext(),"wellPrepared",Toast.LENGTH_SHORT).show();
        Log.e("wellPrepared","wellPrepared谁谁谁水水水水");
        mHandler.sendEmptyMessage(MSG_AUDIO_PREPARED);
        Log.e("wellPrepared","wellPrepared顶顶顶顶顶顶顶顶顶多多");
    }

    public AudioRecorderButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        mDialogManager=new DialogManager(getContext());

        String dir= Environment.getExternalStorageDirectory()+"/immoc_recorder_audios";
        mAudioManager=AudioManager.getInstance(dir);
        mAudioManager.setOnAudioStateListener(this);

        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int checkAudioPermission = ContextCompat.
                        checkSelfPermission(mActivity, Manifest.permission.RECORD_AUDIO);
                Lutil.e(checkAudioPermission+"");
                if (checkAudioPermission != PackageManager.PERMISSION_GRANTED) {
                    mActivity.requestPermission(0,Manifest.permission.RECORD_AUDIO,
                            new Runnable(){
                                @Override
                                public void run() {
                                    //同意
                                    Lutil.e("同意");
                                    SpUtil.putBoolean(getContext(),IS_FIRST_SOUND_RECORDING,false);
                                }
                            },
                            new Runnable(){
                                @Override
                                public void run() {
                                    //拒绝
                                    Lutil.e("拒绝");
                                }
                            });
                    return true;
                }else{
                    SpUtil.putBoolean(getContext(),IS_FIRST_SOUND_RECORDING,false);
                }
                Log.e("ww","onLongClick");
                mReady=true;
                mAudioManager.prepareAudio();
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action=event.getAction();
        int x= (int) event.getX();
        int y= (int) event.getY();
//        Log.e("x",x+"");
//        Log.e("y",y+"");
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            changeState(STATE_RECORDING);
        }else if(event.getAction()==MotionEvent.ACTION_MOVE){
            if(isRecording){
               //根据x,y的坐标,判断是否想要取消
               if(wanttoCancel(x,y)){
                   changeState(STATE_WANT_TO_CANCEL);
               }else{
                   changeState(STATE_RECORDING);
               }
           }
        }else if(event.getAction()==MotionEvent.ACTION_UP){
            Toast.makeText(getContext(),"up",Toast.LENGTH_SHORT).show();
            if(!mReady){
                reset();
                return super.onTouchEvent(event);
            }
            Log.e("isRecording",isRecording+"");
            Log.e("mTime",mTime+"");
            if(!isRecording||mTime<0.6f){
                Boolean aBoolean = SpUtil.getBoolean(getContext(), IS_FIRST_SOUND_RECORDING, true);
                if(!aBoolean){
                    mDialogManager.tooshort();
                    mHandler.sendEmptyMessageDelayed(MSG_DIALOG_DISMISS,1300);
                    Log.e("短11111111","运行了");
                }
                mAudioManager.cancel();
                reset();
                Log.e("短","运行了");
                return super.onTouchEvent(event);
            }
            if(mCurrentState==STATE_RECORDING){
                mDialogManager.dimissDialog();
                Log.e("STATE_RECORDING","运行了");
                mAudioManager.release();
                if(mListener!=null){
                    mListener.onFinish(mTime,mAudioManager.getCurrentFilePath());
                }
            }else if(mCurrentState==STATE_WANT_TO_CANCEL){
                mDialogManager.dimissDialog();
                mAudioManager.cancel();
            }
            reset();
        }else if(event.getAction()==MotionEvent.ACTION_CANCEL){
            reset();
            mAudioManager.cancel();
            Log.e("ACTION_CANCEL","运行了");
//            Log.e("mDialog",mDialogManager.mDialog+"");
//            mDialogManager.dimissDialog();
//            reset();
        }
        return super.onTouchEvent(event);
    }

    //恢复状态及标志位
    public void reset() {
        mReady=false;
        isRecording=false;
        mTime=0;//放在isRecording=false;后面,这时线程执行不了mTime定时增加的代码再把mTime赋值为0
        changeState(STATE_NORMAL);
    }

    private boolean wanttoCancel(int x, int y) {
        if(x<0||x>getWidth()){
            return true;
        }
        if(y<-DISTANCE_Y_CANCEL||y>getHeight()+DISTANCE_Y_CANCEL){
            return true;
        }
        return false;
    }

    private void changeState(int state) {
        if(mCurrentState!=state){
            mCurrentState=state;
            if(mCurrentState==STATE_NORMAL){
                setBackgroundResource(R.drawable.btn_recorder_normal);
                setText(R.string.str_recorder_normal);
            }else if(mCurrentState==STATE_RECORDING){
                setBackgroundResource(R.drawable.btn_recording);
                setText(R.string.str_recorder_recording);
                if(isRecording){
                    mDialogManager.recording();
                }
            }else if(mCurrentState==STATE_WANT_TO_CANCEL){
                setBackgroundResource(R.drawable.btn_recorder_normal);
                setText(R.string.str_recorder_want_cancel);
                mDialogManager.wantToCancel();
            }
        }
    }
}
