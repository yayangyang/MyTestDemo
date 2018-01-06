package mobiesafe74.itheima.com.immoc_recorder.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import mobiesafe74.itheima.com.immoc_recorder.R;
import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.holder.ChatHolder;

/**
 * Created by Administrator on 2017/4/29.
 */

public class RecorderAdapter extends BaseAdapter{

    public static int mMinItemWidth;
    public static int mMaxItemWidth;

    private LayoutInflater mInflater;
    private Context mContext;

    public List<Recorder> mDatas=new ArrayList<Recorder>();

    public RecorderAdapter(Context context, List<Recorder> datas) {
        mContext=context;

        mDatas=datas;

        mInflater=LayoutInflater.from(context);

        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics=new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);

        mMaxItemWidth=(int)(outMetrics.widthPixels*0.7f);
        mMinItemWidth=(int)(outMetrics.widthPixels*0.15f);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Recorder getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        Log.e("www",position+"运行了111");
//        Log.e("www",convertView+"请求");
        ChatHolder holder=null;
        if(convertView==null){
            holder=new ChatHolder();
//            Log.e("www","运行了111");
        }else{
            holder= (ChatHolder) convertView.getTag();
        }
        holder.setContext(mContext);
        holder.setTag(position);
        holder.setData(getItem(position));
        holder.setRecorderAdapter(this);
//        Log.e("时间1111111111",mDatas.get(position).getTime()+"时间");

        return holder.getRootView();
    }

}
