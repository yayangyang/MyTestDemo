package com.imooc.videoplayer.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.bean.AudioItem;
import com.imooc.videoplayer.bean.VideoItem;
import com.imooc.videoplayer.util.Utils;

/**
 * Created by Administrator on 2017/6/10.
 */

public class AudioListAdapter extends CursorAdapter{
    public AudioListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    //创建一个View
    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        //填充出一个view
        View view=View.inflate(context,R.layout.adapter_audio_list,null);
        //用一个ViewHolder保存view中的子控件
        ViewHolder holder=new ViewHolder();
        holder.tv_title= (TextView) view.findViewById(R.id.tv_title);
        holder.tv_artist= (TextView) view.findViewById(R.id.tv_artist);
        //把viewHolder保存到view中
        view.setTag(holder);
        return view;
    }

    //把cursor中的数据绑定到view上进行显示
    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder= (ViewHolder) view.getTag();

        AudioItem item=AudioItem.fromCursor(cursor);
        holder.tv_title.setText(item.getTitle());
        holder.tv_artist.setText(item.getArtist());
    }

    class ViewHolder{
        TextView tv_title;
        TextView tv_artist;
    }
}
