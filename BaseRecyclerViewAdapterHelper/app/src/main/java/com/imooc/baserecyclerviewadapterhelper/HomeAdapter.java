package com.imooc.baserecyclerviewadapterhelper;

import android.widget.ImageView;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/9/9.
 */

public class HomeAdapter extends BaseQuickAdapter<HomeItem, BaseViewHolder> {
    public HomeAdapter(int layoutResId, List data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeItem item) {
        helper.setText(R.id.text, item.getTitle());
        helper.setImageResource(R.id.icon, item.getImageResource());
        // 加载网络图片
        Glide.with(mContext).load(item.getUserAvatar()).crossFade().into((ImageView) viewHolder.getView(R.id.iv));
    }
}