package mobiesafe74.itheima.com.immoc_recorder.manager;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.SettingActivity;
import mobiesafe74.itheima.com.immoc_recorder.adapter.RecorderAdapter;
import mobiesafe74.itheima.com.immoc_recorder.holder.DialogHolder;
import mobiesafe74.itheima.com.immoc_recorder.manager.MediaManager;
import mobiesafe74.itheima.com.immoc_recorder.manager.VoiceManager;

import static com.avos.avoscloud.Messages.StatusType.on;

/**
 * Created by Administrator on 2017/4/28.
 */

public class DialogManager {
    public Dialog mDialog;
    private ImageView mIcon;
    private ImageView mVoice;
    private TextView mLabel;

    private Context mContext;

    private ListView lv_remove_dialog;

    private List<String> mStringList=null;

    public DialogManager(Context context){
        mContext=context;
    }

    //展示语音Dialog
    public void showRecordingDialog(){
        mDialog=new Dialog(mContext, R.style.Theme_AudioDialog);
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.dialog_recorder, null);
        mDialog.setContentView(view);

        mIcon= (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_icon);
        mVoice= (ImageView) mDialog.findViewById(R.id.id_recorder_dialog_voice);
        mLabel= (TextView) mDialog.findViewById(R.id.id_recorder_dialog_label);

        mDialog.show();
    }

    //展示删除语音Dialog
    public void showRemoveVoiceDialog(ArrayList<String> mStringList, final int tag, final RecorderAdapter adapter){
//        if(lv_remove_dialog==null){
//            Log.e("ww","为空");
//        }
        final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        LayoutInflater inflater=LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.remove_dialog, null);
        dialog.setView(view);

        lv_remove_dialog= (ListView) view.findViewById(R.id.lv_remove_dialog);
        lv_remove_dialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //删除
                if(position==3){
                    dialog.dismiss();
                    adapter.mDatas.remove(tag);
                    //打印getView的log在下面的代码之后,这句代码作用应该是通知ondraw时重绘
                    adapter.notifyDataSetChanged();
                    if(tag==VoiceManager.getPosition()){
                        if(VoiceManager.getmAnimView()!=null){//动画执行时才会执行这些代码
                            Log.e("等于","运行了");
//                            VoiceManager.getAnimView().setBackgroundResource(R.drawable.adj);
                            VoiceManager.setAnimView(null);
                            MediaManager.release();
                        }
                    }else if(tag<VoiceManager.getPosition()){
                        if(VoiceManager.getmAnimView()!=null){//动画执行时才会执行这些代码
                            Log.e("小于","运行了");
                            VoiceManager.getAnimView().setBackgroundResource(R.drawable.adj);
                            VoiceManager.setPosition(VoiceManager.getPosition()-1);
                            Log.e("ww",VoiceManager.getPosition()+"dddd");
                        }
                    }
                }
            }
        });
        lv_remove_dialog.setAdapter(new ArrayAdapter<String>(mContext,
                R.layout.dialog_item,mStringList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                DialogHolder holder=null;
                if(convertView==null){
                    holder=new DialogHolder();
                }else{
                    holder= (DialogHolder) convertView.getTag();
                }
                holder.setData(getItem(position));
                return holder.getRootView();
            }
        });
        dialog.show();
    }

    //展示退出登录Dialog
    public Dialog showExitDialog(){
        final AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(UIUtils.getContext(),R.layout.exit_dialog, null);
        dialog.setView(view);//注意这里是setView不是setContentView,写成setContentView没有内容显示

        LinearLayout exitCurrentAccount= (LinearLayout) view.findViewById(R.id.exitCurrentAccount);
        LinearLayout closeWetChat= (LinearLayout) view.findViewById(R.id.closeWetChat);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.exitCurrentAccount){
                    Intent intent = new Intent();
                    intent.setClass(UIUtils.getContext(),LoginActivity.class);
                    UIUtils.getContext().startActivity(intent);
                    SettingActivity sttingActivity = (SettingActivity) mContext;
                    sttingActivity.finish();
                    LoginActivity.sMainActivity.finish();
                }else if (v.getId()==R.id.closeWetChat){

                }
            }
        };
        exitCurrentAccount.setOnClickListener(onClickListener);
        closeWetChat.setOnClickListener(onClickListener);
        dialog.show();
        return dialog;
    }

    public void recording(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.VISIBLE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.recorder);
            mLabel.setText("手指上滑,取消发送");
        }
    }

    public void wantToCancel(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.cancel);
            mLabel.setText("松开手指,取消发送");
        }
    }

    public void tooshort(){
        if(mDialog!=null&&mDialog.isShowing()){
            mIcon.setVisibility(View.VISIBLE);
            mVoice.setVisibility(View.GONE);
            mLabel.setVisibility(View.VISIBLE);

            mIcon.setImageResource(R.drawable.voice_to_short);
            mLabel.setText("录音时间过短");
        }
    }

    public void dimissDialog(){
        Log.e("取消dialog","还没取消dialog");
        Log.e("mDialog",mDialog+"");
        Log.e("mDialog.isShowing()",mDialog.isShowing()+"");
        if(mDialog!=null&&mDialog.isShowing()){
            Log.e("取消dialog","取消了dialog");
            mDialog.dismiss();
            mDialog=null;
        }
    }

    //通过level去更新voice上的图片
    public void updateVoiceLevel(int level){
        if(mDialog!=null&&mDialog.isShowing()){
//            mIcon.setVisibility(View.VISIBLE);
//            mVoice.setVisibility(View.VISIBLE);
//            mLabel.setVisibility(View.VISIBLE);

            int resId=mContext.getResources().getIdentifier(
                    "v"+level,"drawable",mContext.getPackageName());
            mVoice.setImageResource(resId);
        }
    }
}
