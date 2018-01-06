package com.imooc.interfacetestdemo;


//子类与父类同时实现同一个接口或继承自同一个接口的接口,若其中共同拥有的这个接口有T(泛型),需保证这个接口分别被
//父子类实现时传入的T是相同的
public class SearchByAuthorPresenter extends RxPresenter<SearchByAuthorContract.View>
        implements SearchByAuthorContract.Presenter {

    @Override
    public void getSearchResultList(String author) {

    }
}
