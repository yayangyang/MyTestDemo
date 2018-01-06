package mobiesafe74.itheima.com.immoc_recorder.manager;

import android.media.MediaRecorder;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.util.UUID;

/**
 * Created by Administrator on 2017/4/29.
 */

public class AudioManager {

    private MediaRecorder mMediaRecorder;
    private String mDir;
    private String mCurrentFilePath;
    private static AudioManager mInstance;
    private boolean isPrepared;

    public String getCurrentFilePath() {
        return mCurrentFilePath;
    }

    private AudioManager(String dir){
        mDir=dir;
    }

    public interface AudioStateListener{
        void wellPrepared();
    }

    public AudioStateListener mListener;

    public void setOnAudioStateListener(AudioStateListener listener){
        mListener=listener;
    }

    public static AudioManager getInstance(String dir){
        if(mInstance==null){
            synchronized (AudioManager.class){
                if(mInstance==null){
                    mInstance=new AudioManager(dir);
                }
            }
        }
        return mInstance;
    }

    public void prepareAudio(){
        File dir=new File(mDir);
        try {
            isPrepared=false;
            if(!dir.exists()){
                dir.mkdirs();
            }
            String fileName=generateFileName();
//            Log.e("dir",dir+"");
//            Log.e("fileName",fileName+"");
            File file=new File(dir,fileName);

            mCurrentFilePath=file.getAbsolutePath();
            mMediaRecorder=new MediaRecorder();
            mMediaRecorder.setOutputFile(file.getAbsolutePath());//设置输出文件
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置MediaRecorder的音频源为麦克风
            //设置音频的格式
            if(Build.VERSION.SDK_INT>10){
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
            }else{
                mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);
            }
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置音频的编码为amr
            mMediaRecorder.prepare();//prepare执行过程中会使用到权限,此时弹出权限框
            mMediaRecorder.start();
            isPrepared=true;//准备结束
            Log.e("mListener",mListener+"搜索");
            if(mListener!=null){
                mListener.wellPrepared();
            }
        } catch (Exception e) {
            Log.e("错误","错误");
            e.printStackTrace();
        }
    }

    //随机生成文件的名称
    private String generateFileName() {
        return UUID.randomUUID().toString()+".amr";
    }

    public int getVoiceLevel(int maxLevel){
        if(isPrepared){
            try {
                //maxLevel*mMediaRecorder.getMaxAmplitude()//取值范围为1-32767
                return maxLevel*mMediaRecorder.getMaxAmplitude()/32768+1;
            } catch (IllegalStateException e) {
                Log.e("IllegalStateException","执行了");
                e.printStackTrace();
            }
        }
        return 1;
    }

    public void release(){
        if(mMediaRecorder!=null){
            mMediaRecorder.stop();//停止录制
            mMediaRecorder.release();//释放资源
            isPrepared=false;
            mMediaRecorder=null;
        }
    }

    public void cancel(){
        release();
        if(mCurrentFilePath!=null){
            File file=new File(mCurrentFilePath);
            file.delete();
            mCurrentFilePath=null;
        }
    }

}
