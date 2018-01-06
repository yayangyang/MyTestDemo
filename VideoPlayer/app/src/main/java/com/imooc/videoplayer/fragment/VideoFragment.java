package com.imooc.videoplayer.fragment;

import android.content.AsyncQueryHandler;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.activity.VideoPlayerActivity;
import com.imooc.videoplayer.activity.VitamioVideoPlayerActivity;
import com.imooc.videoplayer.adapter.VideoListAdapter;
import com.imooc.videoplayer.bean.VideoItem;
import com.imooc.videoplayer.interfaces.Keys;
import com.imooc.videoplayer.util.Utils;

import java.util.ArrayList;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;

/**
 * Created by Administrator on 2017/6/10.
 */

public class VideoFragment extends BaseFragment {

    private ListView listview;

    @Override
    public int getLayoutResID() {
        return R.layout.fragment_media_list;
    }

    @Override
    public void initView() {
        listview= (ListView) rootView;
    }

    @Override
    public void initListener() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor= (Cursor) parent.getItemAtPosition(position);
                ArrayList<VideoItem> videos=getVideoItems(cursor);
                enterVideoPlayerActivity(videos,position);
            }

        });
    }

    //把Cursor里面的所有数据封装到一个ArrayList中
    private ArrayList<VideoItem> getVideoItems(Cursor cursor) {
        ArrayList<VideoItem> videos=new ArrayList<VideoItem>();
        cursor.moveToFirst();
        do{
            videos.add(VideoItem.fromCursor(cursor));
        }while(cursor.moveToNext());
        return videos;
    }

    //进入视频播放界面
    private void enterVideoPlayerActivity(ArrayList<VideoItem> videos,int position) {
        Intent intent = new Intent(getActivity(), VideoPlayerActivity.class);
//        Intent intent = new Intent(getActivity(), VitamioVideoPlayerActivity.class);
        intent.putExtra(Keys.ITEMS,videos);
        intent.putExtra(Keys.CURRENT_POSITION,position);
        startActivity(intent);
    }

    @Override
    public void initData() {
//        getActivity().getContentResolver().query()//这个查询方法会运行在主线程
        AsyncQueryHandler queryHandler=new AsyncQueryHandler(getActivity().getContentResolver()) {
            //查询到数据的回调方法,此时这个方法运行在主线程
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                Log.e("ww",cursor.getCount()+"ff");
//                Utils.printCursor(cursor);
                VideoListAdapter adapter=new VideoListAdapter(getActivity(),cursor);
                listview.setAdapter(adapter);
            }
        };
        int token=0;//相当于Message.what
        Object cookie=null;//相当于Message.obj
        Uri uri= MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection={//指定要查询哪些列
                MediaStore.Video.Media._ID,//CursorAdapter必须加上这个列,不然CursorAdapter不能工作
                MediaStore.Video.Media.TITLE, MediaStore.Video.Media.DURATION,
                MediaStore.Video.Media.SIZE,MediaStore.Video.Media.DATA
        };
        String selection=null;//指定查询条件
        String[] selectionArgs=null;//指定查询条件中的参数
        String orderBy=MediaStore.Video.Media.TITLE+" ASC";//指定为升序
        //运行在子线程
        queryHandler.startQuery(token,cookie,uri,projection,selection,selectionArgs,orderBy);
    }

    @Override
    public void onclick(View v, int id) {

    }

}
