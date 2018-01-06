package com.imooc.videoplayer.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.bean.Lyric;
import com.imooc.videoplayer.util.Lutil;
import com.imooc.videoplayer.util.LyricLoaderUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/14.
 */

public class lyricView extends View {
    private int highlightColor= Color.GREEN;//高亮歌词的颜色
    private int defaultColor= Color.WHITE;//默认歌词的颜色
    private float highlightSize;//高亮歌词的大小
    private float defaultSize;//默认歌词的大小
    private ArrayList<Lyric> mLyrics;
    private Paint mPaint;
    private int highlightIndex;
    private float mHightlightY;//高亮行的y坐标
    private float mRowHeight;//行高
    private int mCurrentPosition;

    public lyricView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Log.e("lyricView","init");
        highlightSize=getResources().getDimension(R.dimen.highlight_size);
        defaultSize=getResources().getDimension(R.dimen.default_size);

        mPaint = new Paint();
        mPaint.setColor(defaultColor);
        mPaint.setTextSize(defaultSize);
        mPaint.setAntiAlias(true);//抗锯齿

        mRowHeight = getTextHeight("撒")+30;

        //模拟歌词数据
//        highlightIndex=4;
//        mLyrics=new ArrayList<Lyric>();
//        for (int i=0;i<10;i++){
//            mLyrics.add(new Lyric(i*2000,"我明天将会拥有"+i));
//        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mHightlightY = getHeight()/2+getTextHeight("撒")/2;
        if(mLyrics==null||mLyrics.size()==0){
            drawCenterText(canvas,"找不到对应的歌词");
            return;
        }
        Log.e("size",mLyrics.size()+"");
        //取出高亮行歌词
        Lyric lyric = mLyrics.get(highlightIndex);
        if(highlightIndex!=mLyrics.size()-1){
            //高亮行歌词已经显示的时间=当前播放时间-高亮行的开始显示时间
            int showedTime=mCurrentPosition-lyric.getStartShowPosition();
            //这里刚开始歌词的开始显示时间比当前播放时间大,刚开始时showedTime为负值
//            Log.e("lyric",lyric.getStartShowPosition()+"");
            //总显示时间=下一行歌词的开始显示时间-高亮行的开始显示时间
            int totalTime=mLyrics.get(highlightIndex+1).getStartShowPosition()-
                    lyric.getStartShowPosition();
            float scale=((float)showedTime)/totalTime;
//            Log.e("scale",scale+"");
            float translateY=scale*mRowHeight;
            canvas.translate(0,-translateY);//只在一次draw之中有效,且在同一次draw中可叠加
        }

        Log.e("mHightlightY11",mHightlightY+"");
        //画高亮行上面的歌词
        for(int i=0;i<highlightIndex;i++){
            float y=mHightlightY-(highlightIndex-i)*mRowHeight;
            drawHorizontalText(canvas,mLyrics.get(i).getText(),y,false);
        }
        drawCenterText(canvas,mLyrics.get(highlightIndex).getText());
        //画高亮行下面的歌词
        for(int i=highlightIndex+1;i<mLyrics.size();i++){
            float y=mHightlightY+(i-highlightIndex)*mRowHeight;
            drawHorizontalText(canvas,mLyrics.get(i).getText(),y,false);
        }
    }

    //画水平和垂直都居中的文本
    private void drawCenterText(Canvas canvas, String text) {
        Log.e("mHightlightY22",mHightlightY+"");
        drawHorizontalText(canvas, text, mHightlightY,true);
    }

    //画水平居中的文本
    private void drawHorizontalText(Canvas canvas, String text, float y,boolean isHighLight) {
        mPaint.setTextSize(isHighLight?highlightSize:defaultSize);
        mPaint.setColor(isHighLight?highlightColor:defaultColor);
        //使用画笔计算字符串宽度也要使用同一画笔画,不然即使重新设置计算结果也不准确(自己理解)
        int textWidth=getTextWidth(text);
        float x=getWidth()/2-textWidth/2;
//        Log.e("textWidth",textWidth+"");
//        Log.e("x",x+"");
//        Log.e("highlightSize",highlightSize+"");
//        Log.e("defaultlightSize",defaultlightSize+"");
        canvas.drawText(text,x,y,mPaint);
    }

    //获取文本宽
    private int getTextWidth(String text) {
        Rect bounds=new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds);
        return bounds.width();
    }

    //获取文本高
    private int getTextHeight(String text) {
        Rect bounds=new Rect();
        mPaint.getTextBounds(text,0,text.length(),bounds);
        return bounds.height();
    }


    //更新当前的播放位置
    public void updatePosition(int currentPosition) {
        if(mLyrics==null){
            return;
        }
        mCurrentPosition = currentPosition;
        for(int i=0;i<mLyrics.size();i++){
            int startShowPosition = mLyrics.get(i).getStartShowPosition();
            Log.e("currentPosition",currentPosition+"");
            if(currentPosition>startShowPosition){
                if(i==mLyrics.size()-1){
                    highlightIndex=i;
                    break;
                }else if(currentPosition<mLyrics.get(i+1).getStartShowPosition()){
                    highlightIndex=i;
                    break;
                }
            }
        }
        Log.e("第一个","invalidate前");
        invalidate();
    }


    //设置音乐路径(这个方法内部会加载这个音乐路径下的相应歌词,并解析显示出来)
    public void setMusicPath(String musicPath) {
        Log.e("setMusicPath",musicPath+"ffffffffff");
        highlightIndex=0;
        mLyrics = LyricLoaderUtil.loadLyric(musicPath);
    }
}

