package mobiesafe74.itheima.com.immoc_recorder.bean;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Comparator;

import mobiesafe74.itheima.com.immoc_recorder.Utils.PinYinUtil;

/**
 * Created by Administrator on 2017/5/8.
 */

public class addressbook implements Comparable<addressbook>{
    private int image;
    private String name;
    private String pinyin;
    private String CreatedAt;
    private String UpdatedAt;
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public addressbook(String name) {
        this.name=name;
        setPinyin(PinYinUtil.getPinyin(name));
    }

    public String getCreatedAt() {
        return CreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        CreatedAt = createdAt;
    }

    public String getUpdatedAt() {
        return UpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        UpdatedAt = updatedAt;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        if(pinyin!=null){
            pinyin=pinyin.toUpperCase();//由于得到的拼音是以大写展示,当不是汉字时每个字符均需转换为大写才可比较
        }
//        Log.e("pinyin",pinyin+"");
        this.pinyin = pinyin;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(addressbook another) {
        return getPinyin().compareTo(another.getPinyin());
    }

}
