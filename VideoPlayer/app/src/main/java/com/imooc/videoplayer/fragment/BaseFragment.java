package com.imooc.videoplayer.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.imooc.videoplayer.R;
import com.imooc.videoplayer.interfaces.UiOperation;
import com.imooc.videoplayer.util.Utils;

/**
 * Created by Administrator on 2017/6/10.
 */

public abstract class BaseFragment extends Fragment implements UiOperation{
    protected View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResID(), null);
        initView();
        initListener();
        initData();
        return rootView;
    }

    //在屏幕中间显示一个Toast
    public void showToast(String text){
        Utils.showToast(text,getActivity());
    }

    @Override
    public void onClick(View v) {
        if(v.getId()== R.id.btn_back){//处理共同操作
            getActivity().finish();
        }else{
            //如果单击的不是返回按钮,则还是由子类去做处理
            onclick(v,v.getId());
        }
    }

}
