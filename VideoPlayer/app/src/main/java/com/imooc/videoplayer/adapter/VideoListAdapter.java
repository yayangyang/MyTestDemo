package com.imooc.videoplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.bean.VideoItem;
import com.imooc.videoplayer.util.Utils;

/**
 * Created by Administrator on 2017/6/10.
 */

public class VideoListAdapter extends CursorAdapter{
    public VideoListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    //创建一个View
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //填充出一个view
        View view=View.inflate(context,R.layout.adapter_video_list,null);
        //用一个ViewHolder保存view中的子控件
        ViewHolder holder=new ViewHolder();
        holder.tv_title= (TextView) view.findViewById(R.id.tv_title);
        holder.tv_duration= (TextView) view.findViewById(R.id.tv_duration);
        holder.tv_size= (TextView) view.findViewById(R.id.tv_size);
        //把viewHolder保存到view中
        view.setTag(holder);
        return view;
    }

    //把cursor中的数据绑定到view上进行显示
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder= (ViewHolder) view.getTag();

        VideoItem item=VideoItem.fromCursor(cursor);
        holder.tv_title.setText(item.getTitle());
        holder.tv_size.setText(Formatter.formatFileSize(context,item.getSize())+"");
        CharSequence time= Utils.formatMillis(item.getDuration());
        holder.tv_duration.setText(time+"");
    }

    class ViewHolder{
        TextView tv_title;
        TextView tv_duration;
        TextView tv_size;
    }
}
