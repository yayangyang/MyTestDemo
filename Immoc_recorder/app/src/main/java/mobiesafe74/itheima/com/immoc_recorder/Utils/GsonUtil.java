package mobiesafe74.itheima.com.immoc_recorder.Utils;

import com.google.gson.Gson;

import mobiesafe74.itheima.com.immoc_recorder.bean.Recorder;
import mobiesafe74.itheima.com.immoc_recorder.bean.receiveMessage;

/**
 * Created by Administrator on 2017/5/13.
 */

public class GsonUtil {
    public static Gson gson = new Gson();
    public static receiveMessage jiexi(String string){
        return gson.fromJson(string, receiveMessage.class);
    }
}
