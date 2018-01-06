package com.imooc.interfacetestdemo;

public class RxPresenter<T extends BaseContract.BaseView>
        implements BaseContract.BasePresenter<T> {

    @Override
    public void attachView(T view) {

    }

    @Override
    public void detachView() {

    }
}
