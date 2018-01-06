package com.imooc.videoplayer.fragment;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.activity.AudioPlayerActivity;
import com.imooc.videoplayer.activity.VideoPlayerActivity;
import com.imooc.videoplayer.adapter.AudioListAdapter;
import com.imooc.videoplayer.adapter.VideoListAdapter;
import com.imooc.videoplayer.bean.AudioItem;
import com.imooc.videoplayer.bean.VideoItem;
import com.imooc.videoplayer.interfaces.Keys;
import com.imooc.videoplayer.util.Utils;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/10.
 */

public class AudioFragment extends BaseFragment {
    private ListView mListView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_media_list;
    }

    @Override
    public void initView() {
        mListView= (ListView) rootView;
    }

    @Override
    public void initListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor) parent.getItemAtPosition(position);
                ArrayList<AudioItem> audios=getAudioItems(cursor);
                enterAudioPlayerActivity(audios,position);
            }

        });
    }

    //把Cursor里面的所有数据封装到一个ArrayList中
    private ArrayList<AudioItem> getAudioItems(Cursor cursor) {
        ArrayList<AudioItem> audios=new ArrayList<AudioItem>();
        cursor.moveToFirst();
        do{
            audios.add(AudioItem.fromCursor(cursor));
        }while(cursor.moveToNext());
        return audios;
    }

    //进入视频播放界面
    private void enterAudioPlayerActivity(ArrayList<AudioItem> videos,int position) {
        Intent intent = new Intent(getActivity(), AudioPlayerActivity.class);
        intent.putExtra(Keys.ITEMS,videos);
        intent.putExtra(Keys.CURRENT_POSITION,position);
        startActivity(intent);
    }

    @Override
    public void initData() {
        AsyncQueryHandler queryHandler=new AsyncQueryHandler(getActivity().getContentResolver()) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
//                Utils.printCursor(cursor);
                AudioListAdapter adapter=new AudioListAdapter(getActivity(),cursor);
                mListView.setAdapter(adapter);
            }
        };
        int token=0;
        Object cookie=null;
        Uri uri= MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection={//指定要查询哪些列
                MediaStore.Audio.Media._ID,//CursorAdapter必须加上这个列,不然CursorAdapter不能工作
                MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.DATA
        };
        String selection=null;//指定查询条件
        String[] selectionArgs=null;//指定查询条件中的参数
        String orderBy=MediaStore.Audio.Media.TITLE+" ASC";//指定为升序
        //运行在子线程
        queryHandler.startQuery(token,cookie,uri,projection,selection,selectionArgs,orderBy);
    }

    @Override
    public void onclick(View v, int id) {

    }

}
