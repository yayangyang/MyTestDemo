package com.imooc.interfacetestdemo;


public interface SearchByAuthorContract {

    interface View extends BaseContract.BaseView {
        void showSearchResultList();
    }

    //这里的这个View不是控件View,是SearchByAuthorContract.View
    interface Presenter extends BaseContract.BasePresenter<View> {
        void getSearchResultList(String author);
    }

}
