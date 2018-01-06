package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMConversation;

import java.util.ArrayList;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.Utils.sendUtil;
import mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.bean.WetChat;
import mobiesafe74.itheima.com.immoc_recorder.manager.MediaManager;
import mobiesafe74.itheima.com.immoc_recorder.manager.VoiceManager;
import mobiesafe74.itheima.com.immoc_recorder.manager.DialogManager;

import static mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter.mMaxItemWidth;
import static mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter.mMinItemWidth;

/**
 * Created by Administrator on 2017/5/1.
 */

public class ChatHolder extends BaseHolder<Recorder>{

    private ImageView icon_self,icon_others;
    private LinearLayout recorder_length_self,recorder_length_others;
    private View recorder_anim_self,recorder_anim_others;
    private TextView recorder_time_self,recorder_time_others;
    private TextView wz_self,wz_others;
    private ImageView picture_self,picture_others;
    private RelativeLayout self,others;
    private RecorderAdapter mRecorderAdapter;

    public RecorderAdapter getRecorderAdapter() {
        return mRecorderAdapter;
    }

    public void setRecorderAdapter(RecorderAdapter recorderAdapter) {
        mRecorderAdapter = recorderAdapter;
    }
    private View view=null;

    private  DialogManager mDialogManager;

    final Drawable drawable_self = UIUtils.getContext()
            .getResources().getDrawable(R.drawable.chatto_bg_focused, null);
    final Drawable drawable_others = UIUtils.getContext()
            .getResources().getDrawable(R.drawable.chatto_bg_focused_others, null);

    @Override
    public void initData() {}

    @Override
    public View initView() {
        view=View.inflate(UIUtils.getContext(), R.layout.item_recorder,null);
        initUI();
        view.setTag(this);
        return view;
    }

    @Override
    protected void refreshView(final Recorder data) {
//        Log.e("getTag",getTag()+"啥啥啥");
        if(getTag()==VoiceManager.getPosition()){
            if(VoiceManager.getAnimView()!=null){
//                Log.e("getPosition",VoiceManager.getPosition()+"wwwwwwwwwwwwwww");
                Log.e("getTag",getTag()+"wwwwwwwwwwwwwww");
                AnimationDrawable anim=null;
                if(data.isSelf()){
                    VoiceManager.setmAnimView(recorder_anim_self);
                    recorder_anim_self.setBackgroundResource(R.drawable.play_anim);
                    anim = (AnimationDrawable) recorder_anim_self.getBackground();
                }else{
                    VoiceManager.setAnimView(recorder_anim_others);
                    recorder_anim_others.setBackgroundResource(R.drawable.play_anim);
                    anim = (AnimationDrawable) recorder_anim_others.getBackground();
                }
                anim.start();
            }
        }else{
            Log.e("wwwww",getTag()+"搜索");
            recorder_anim_self.setBackgroundResource(R.drawable.adj);
        }
        mDialogManager=new DialogManager(getContext());
        if(data.isSelf()){
            self.setVisibility(View.VISIBLE);
            others.setVisibility(View.GONE);
            hideSelf();
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) recorder_length_self.getLayoutParams();
            if(data.getType()==Recorder.TYPE_VOICE){
                recorder_length_self.setVisibility(View.VISIBLE);
                recorder_anim_self.setVisibility(View.VISIBLE);
                recorder_time_self.setVisibility(View.VISIBLE);
                lp.width= (int) (mMinItemWidth+(mMaxItemWidth/60f*getData().getTime()));
//        Log.e("lp.width",lp.width+"");
                if(lp.width>mMinItemWidth+mMaxItemWidth){
                    lp.width= mMinItemWidth+mMaxItemWidth;
                }
                recorder_length_self.setLayoutParams(lp);
                recorder_time_self.setText(Math.round(data.getTime())+"\"");
            }else if(data.getType()==Recorder.TYPE_WORD){
                recorder_length_self.setVisibility(View.VISIBLE);
                wz_self.setVisibility(View.VISIBLE);
                wz_self.setText(data.getWz());
                lp.width= RelativeLayout.LayoutParams.WRAP_CONTENT;
                recorder_length_self.setLayoutParams(lp);
            }else if(data.getType()==Recorder.TYPE_IMAGE){
                recorder_length_self.setVisibility(View.VISIBLE);
                picture_self.setVisibility(View.VISIBLE);
                picture_self.setImageResource(data.getDrawableId());
                lp.width= RelativeLayout.LayoutParams.WRAP_CONTENT;
                recorder_length_self.setLayoutParams(lp);
            }
        }else{
            others.setVisibility(View.VISIBLE);
            self.setVisibility(View.GONE);
            hideOthers();
            RelativeLayout.LayoutParams lp= (RelativeLayout.LayoutParams) recorder_length_others.getLayoutParams();
            if(data.getType()==Recorder.TYPE_VOICE){
                recorder_length_others.setVisibility(View.VISIBLE);
                recorder_anim_others.setVisibility(View.VISIBLE);
                recorder_time_others.setVisibility(View.VISIBLE);
//                Log.e("时间",getData().getTime()+"时间");
//                Log.e("时间222",getData().getAudioTime()+"时间");
                lp.width= (int) (mMinItemWidth+(mMaxItemWidth/60f*getData().getTime()));
                if(lp.width>mMinItemWidth+mMaxItemWidth){
                    lp.width= mMinItemWidth+mMaxItemWidth;
                }
                recorder_length_others.setLayoutParams(lp);
                recorder_time_others.setText(Math.round(data.getTime())+"\"");
            }else if(data.getType()==Recorder.TYPE_WORD){
                recorder_length_others.setVisibility(View.VISIBLE);
                wz_others.setVisibility(View.VISIBLE);
                wz_others.setText(data.getWz());
                lp.width= RelativeLayout.LayoutParams.WRAP_CONTENT;
                recorder_length_others.setLayoutParams(lp);
            }else if(data.getType()==Recorder.TYPE_IMAGE){
                recorder_length_others.setVisibility(View.VISIBLE);
                picture_others.setVisibility(View.VISIBLE);
                picture_others.setImageResource(data.getDrawableId());
                lp.width= RelativeLayout.LayoutParams.WRAP_CONTENT;
                recorder_length_others.setLayoutParams(lp);
            }
        }
    }

    private void hideSelf(){
        recorder_anim_self.setVisibility(View.GONE);
        recorder_time_self.setVisibility(View.GONE);
        wz_self.setVisibility(View.GONE);
        picture_self.setVisibility(View.GONE);
        recorder_length_self.setVisibility(View.GONE);
    }

    private void hideOthers(){
        recorder_anim_others.setVisibility(View.GONE);
        recorder_time_others.setVisibility(View.GONE);
        wz_others.setVisibility(View.GONE);
        picture_others.setVisibility(View.GONE);
        recorder_length_others.setVisibility(View.GONE);
    }

    private void initUI() {
        icon_self= (ImageView) view.findViewById(R.id.icon_self);
        icon_others= (ImageView) view.findViewById(R.id.icon_others);
        recorder_length_self= (LinearLayout) view.findViewById(R.id.recorder_length_self);
        recorder_length_others= (LinearLayout) view.findViewById(R.id.recorder_length_others);
        recorder_anim_self=view.findViewById(R.id.recorder_anim_self);
        recorder_anim_others=view.findViewById(R.id.recorder_anim_others);
        recorder_time_self= (TextView) view.findViewById(R.id.recorder_time_self);
        recorder_time_others= (TextView) view.findViewById(R.id.recorder_time_others);
        wz_self= (TextView) view.findViewById(R.id.wz_self);
        wz_others= (TextView) view.findViewById(R.id.wz_others);
        picture_self= (ImageView) view.findViewById(R.id.picture_self);
        picture_others= (ImageView) view.findViewById(R.id.picture_others);
        self= (RelativeLayout) view.findViewById(R.id.self);
        others= (RelativeLayout) view.findViewById(R.id.others);

        View.OnLongClickListener onLongClickListener = new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                Log.e("ww","点击了1111111");
                ArrayList<String> mStringList = new ArrayList<String>();
                mStringList.add("使用听筒模式");
                mStringList.add("收藏");
                mStringList.add("转换为文字(仅普通话)");
                mStringList.add("删除");
                mStringList.add("更多");
                mDialogManager.showRemoveVoiceDialog(mStringList, getTag(), getRecorderAdapter());
                return true;
            }
        };
        recorder_length_self.setOnClickListener(new MyListener());
        recorder_length_self.setOnLongClickListener(onLongClickListener);
        //这里触摸监听若同时设置recorder_length_self与recorder_length_others的drawable(同一个drawable),
        //self的变深色后不会取消,others不变色
        recorder_length_self.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable_self.setColorFilter(0x33000000, PorterDuff.Mode.MULTIPLY);
                    recorder_length_self.setBackground(drawable_self);
                } else if (event.getAction() == MotionEvent.ACTION_UP ||
                        event.getAction() == MotionEvent.ACTION_CANCEL) {
                    drawable_self.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    recorder_length_self.setBackground(drawable_self);
                }
                return false;
            }
        });
        recorder_length_others.setOnClickListener(new MyListener());
        recorder_length_others.setOnLongClickListener(onLongClickListener);
        recorder_length_others.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    drawable_others.setColorFilter(0x33000000, PorterDuff.Mode.MULTIPLY);
                    recorder_length_others.setBackground(drawable_others);
                } else if (event.getAction() == MotionEvent.ACTION_UP ||
                        event.getAction() == MotionEvent.ACTION_CANCEL) {
                    drawable_others.setColorFilter(Color.WHITE, PorterDuff.Mode.MULTIPLY);
                    recorder_length_others.setBackground(drawable_others);
                }
                return false;
            }
        });
    }

    class MyListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            if(getData()!=null){
                if(getData().getType()==Recorder.TYPE_VOICE){
//                    Log.e("ww","点击了22222");
                    View mAnimView= VoiceManager.getAnimView();
                    if(mAnimView!=null){
                        VoiceManager.getAnimView().setBackgroundResource(R.drawable.adj);
                        VoiceManager.setAnimView(null);
                        MediaManager.release();
                        if(VoiceManager.getPosition()==getTag()) return;//判断点击是否是同一段语音
                    }
                    Log.e("getTag()",getTag()+"ssssssss");
                    VoiceManager.setPosition(getTag());//记录点击语音position
                    //播放动画
                    mAnimView=getRootView().findViewById(R.id.recorder_anim_self);
                    VoiceManager.setAnimView(mAnimView);
//                    Log.e("getAnimView",VoiceManager.getAnimView()+"");
                    mAnimView.setBackgroundResource(R.drawable.play_anim);
                    AnimationDrawable anim = (AnimationDrawable) mAnimView.getBackground();
                    anim.start();
                    //播放音频
                    MediaManager.playSound(getData().getFilePath(), new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mp) {
                            if(VoiceManager.getAnimView()!=null){
//                                Log.e("ww","运行完");
                                VoiceManager.getmAnimView().setBackgroundResource(R.drawable.adj);
                                VoiceManager.setAnimView(null);//置为空运行完后再次点击播放才不需要点击两次
                            }
                        }
                    });
                }
            }
        }
    }
}
