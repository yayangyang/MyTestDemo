package com.yayangyang.findlastvisibleitempositiontestdemo;

import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/12/24.
 */

public class MyAdapter extends BaseQuickAdapter<String,BaseViewHolder> {

    public MyAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
//        Log.e("convert-item:",item);
//        Log.e("helper:",helper+"ee");
        helper.setText(R.id.tv,item);
    }
}
