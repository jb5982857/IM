package com.nanshan.we12talk.module;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by jiangbo on 2018/9/6.
 * presenter的基础类
 */

public abstract class BasePresenter<T extends IView, M extends BaseModel> {
    protected T mActivity;
    protected M mModel;

    //用户控制网络请求的生命周期，防止activity或者fragment内存泄漏
    // 需要在每个http请求的时候将这个subscription添加到这个对象中进行管理
    protected CompositeSubscription compositeSubscription;

    public BasePresenter(T t, M m) {
        mActivity = t;
        mModel = m;
        compositeSubscription = new CompositeSubscription();
    }

    public void onDestroy() {
        //结束异步请求.
        compositeSubscription.unsubscribe();
    }
}
