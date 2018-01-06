package com.yayangyang.arraylisttestdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//ArrayList删除一个数据时其后面的数据索引会向前移动,
// 所以循环删除mArrayList的元素只能删除一般而且是间隔删除,清空mArrayList用clear方法或用removeAll方法
public class MainActivity extends AppCompatActivity {

    private ArrayList<String> mArrayList=new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        for(int i=0;i<10;i++){
            mArrayList.add(i+"");
        }

        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("1");
        arrayList.add("5");
        arrayList.add("8");
        arrayList.add("9");

        mArrayList.removeAll(arrayList);

        mArrayList.subList(0,3);//subList不赋值不会影响原来集合

//        for(int i=0;i<mArrayList.size();i++){
//            mArrayList.remove(i);
//            Log.e("wwwwwwwwww","eeeeee"+i);
//        }
//        mArrayList.clear();

        for(int i=0;i<mArrayList.size();i++){
            Log.e("wwwwwwwwww","eeeeee"+mArrayList.get(i));
        }

    }
}
