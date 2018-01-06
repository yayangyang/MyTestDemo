package com.imooc.testdemo;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/10/23.
 */

public class quickAdapter extends BaseQuickAdapter<String,BaseViewHolder> {
    public quickAdapter(@LayoutRes int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {
        Log.e("quickAdapter","convert"+helper.getLayoutPosition());
        Log.e("quickAdapter","convert"+item);
        helper.setText(R.id.title,item);
    }
}
