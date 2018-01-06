package mobiesafe74.itheima.com.immoc_recorder.holder;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.Utils.UIUtils;
import mobiesafe74.itheima.com.immoc_recorder.activity.LoginActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.PersonalActivity;
import mobiesafe74.itheima.com.immoc_recorder.activity.SettingActivity;

import static mobiesafe74.itheima.com.immoc_recorder.R.id.Scan;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.Shake;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.Shopping;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.bottle;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.friendplace;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.game;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.nearby;
import static mobiesafe74.itheima.com.immoc_recorder.R.id.rl_right;

/**
 * Created by Administrator on 2017/5/7.
 */

public class MySelfHolder extends BaseHolder implements View.OnClickListener{

    private RelativeLayout portrait,album,collection,wallet,cardpackage,expression,setting;
    private TextView tv_start;
    private ImageView iv_end;

    @Override
    public void initData() {}

    @Override
    public View initView() {
        View view = View.inflate(UIUtils.getContext(), R.layout.myself, null);
        portrait = (RelativeLayout) view.findViewById(R.id.portrait);
        portrait.setOnClickListener(this);
        ((TextView)portrait.findViewById(R.id.tv_start)).setText(LoginActivity.myName);
        ((ImageView)portrait.findViewById(R.id.iv_end)).setImageResource(R.drawable.ic_launcher);
        album = (RelativeLayout) view.findViewById(R.id.album);
        album.setOnClickListener(this);
        ((TextView)album.findViewById(R.id.tv_start)).setText("相册");
        collection = (RelativeLayout) view.findViewById(R.id.collection);
        collection.setOnClickListener(this);
        ((TextView)collection.findViewById(R.id.tv_start)).setText("收藏");
        wallet = (RelativeLayout) view.findViewById(R.id.wallet);
        wallet.setOnClickListener(this);
        ((TextView)wallet.findViewById(R.id.tv_start)).setText("钱包");
        cardpackage = (RelativeLayout) view.findViewById(R.id.cardpackage);
        cardpackage.setOnClickListener(this);
        ((TextView)cardpackage.findViewById(R.id.tv_start)).setText("卡包");
        expression= (RelativeLayout) view.findViewById(R.id.expression);
        ((TextView)expression.findViewById(R.id.tv_start)).setText("表情");
        setting= (RelativeLayout) view.findViewById(R.id.setting);
        setting.setOnClickListener(this);
        ((TextView)setting.findViewById(R.id.tv_start)).setText("设置");
        return view;
    }

    @Override
    protected void refreshView(Object data) {

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.portrait){
            //头像
            Log.e("头像","头像");
            Intent intent = new Intent();
            intent.setClass(UIUtils.getContext(), PersonalActivity.class);
            UIUtils.getContext().startActivity(intent);
        }else if(v.getId()==R.id.album){
            //相册
        }else if(v.getId()==R.id.collection){
            //收藏
        }else if(v.getId()==R.id.wallet){
            //钱包
        }else if(v.getId()==R.id.cardpackage){
            //卡包
        }else if(v.getId()==R.id.expression){
            //表情
        }else if(v.getId()==R.id.setting){
            //设置
            Intent intent = new Intent();
            intent.setClass(UIUtils.getContext(), SettingActivity.class);
            UIUtils.getContext().startActivity(intent);
        }
    }
}
