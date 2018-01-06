package com.imooc.rxjava2test;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        test();
    }

    private void test() {

        DisposableObserver disposableObserver = new DisposableObserver<String>() {
            @Override
            public void onNext(String s) {
                Log.e(TAG, "开始");
                Log.e(TAG + "数据", String.valueOf(s));
                Log.e(TAG, "结束");
            }

            @Override
            public void onComplete() {
                Log.e(TAG, "onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, e.toString(), e);
            }
        };

        mCompositeDisposable.add(
                Observable.concat(
                        getObservableA(null),
                        getObservableB(null),
                        getObservableA(null),
                        getObservableB(null))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(disposableObserver));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 如果退出程序，就清除后台任务
        mCompositeDisposable.clear();
    }

    private Observable<String> getObservableA(Object o) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(500); // 假设此处是耗时操作
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("ww","123");
                    }
                }.start();

                return "A";
            }
        });
    }

    private Observable<String> getObservableB(Object o) {
        return Observable.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                try {
                    Thread.sleep(1000); // 假设此处是耗时操作
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new Thread(){
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.e("ww","123");
                    }
                }.start();

                return "B";
            }
        });
    }
}
