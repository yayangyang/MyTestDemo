package com.imooc.videoplayer.util;

import android.util.Log;

import com.imooc.videoplayer.bean.Lyric;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

public class LyricLoaderUtil {

//	public static void main(String[] args) {
//		String musicPath="F:\\手机影音项目\\资料\\视频和音乐资源\\test\\audio\\TongHua.mp3";
//		ArrayList<Lyric> lyrics=loadLyric(musicPath);
//		for (Lyric lyric : lyrics) {
//			System.out.println(lyric);
//		}
//	}

	/**
	 * 加载歌词
	 * @param musicPath
	 * @return
	 */
	public static ArrayList<Lyric> loadLyric(String musicPath) {
		ArrayList<Lyric> lyrics=null;
		String prefix = musicPath.substring(0, musicPath.lastIndexOf("."));
		//拼接歌词文件
		File lrcFile=new File(prefix+".lrc");
		File textFile=new File(prefix+".txt");
		if(lrcFile.exists()){
			lyrics=readFile(lrcFile);
		}else if(textFile.exists()){
			lyrics=readFile(textFile);
		}
		if(lyrics==null){
			return null;
		}
		//按时间的先后顺序进行排序
		Collections.sort(lyrics, new Comparator<Lyric>() {
			@Override
			public int compare(Lyric o1, Lyric o2) {
				return Integer.valueOf(o1.getStartShowPosition())
						.compareTo(o2.getStartShowPosition());
			}
		});
		for(int i=0;i<lyrics.size();i++){
			lyrics.get(i).toString();
		}
		Log.e("LyricLoaderUtil",lyrics.size()+"");
		return lyrics;
	}

	/**
	 * 读取歌词文件
	 * @param lrcFile
	 * @return
	 */
	private static ArrayList<Lyric> readFile(File lrcFile) {
		ArrayList<Lyric> lyrics=new ArrayList<Lyric>();
		try {
			InputStream in = new FileInputStream(lrcFile);
			BufferedReader reader=new BufferedReader(new InputStreamReader(in,"GBK"));
			String line;
			while((line=reader.readLine())!=null){
				String[] strings = line.split("]");
				String lyricText=strings[strings.length-1];//获取歌词文本
				//遍历所有时间
				for(int i=0;i<strings.length-1;i++){
					int startShowPosition=parseTime(strings[i]);
					lyrics.add(new Lyric(startShowPosition, lyricText));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return lyrics;
	}

	/**
	 * 解析[02:09.20这样的字符串,解析成毫秒值
	 * @param time
	 * @return
	 */
	private static int parseTime(String time) {
		String minuteStr=time.substring(1, 3);//取出02
		String secondStr=time.substring(4, 6);//取出09
		String millisStr=time.substring(7, 9);//取出20
		int minuteMills=Integer.parseInt(minuteStr)*60*1000;
		int secondMillis=Integer.parseInt(secondStr)*1000;
		int millis=Integer.parseInt(millisStr)*10;
		return minuteMills+secondMillis+millis;
	}

}
