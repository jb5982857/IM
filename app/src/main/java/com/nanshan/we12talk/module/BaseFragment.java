package com.nanshan.we12talk.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanshan.support.utils.LogUtils;

import butterknife.ButterKnife;

/**
 * Created by jiangbo on 2018/9/28.
 * fragment基础类
 */

public abstract class BaseFragment<T extends BasePresenter> extends Fragment implements IView<T> {
    //控件是否加载完成
    private boolean viewInit;
    //数据是否已经加载，防止滑动回来的时候重新加载
    private boolean dataLoaded;
    //该fragment是否对用户可见
    private boolean visibleToUser;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtils.d(this.getClass().getSimpleName() + " onActivityCreated");
        if (getView() != null) {
            ButterKnife.bind(getView());
        }
        viewInit = true;
        prepareFetchData();
    }

    //这个方法是回调当前fragment是否是对用户可见对，true为可见，false为隐藏
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        LogUtils.d(this.getClass().getSimpleName() + " setUserVisibleHint " + isVisibleToUser);
        visibleToUser = isVisibleToUser;
        prepareFetchData();
    }

    //加载数据
    public abstract void loadData();

    @Override
    public void onDestroyView() {
        LogUtils.d("onDestroyView");
        super.onDestroyView();
        viewInit = false;
    }

    //准备加载数据
    public void prepareFetchData(boolean isFouce) {
        if (viewInit && visibleToUser && (!dataLoaded || isFouce)) {
            loadData();
            dataLoaded = true;
        }
    }

    private void prepareFetchData() {
        prepareFetchData(false);
    }
}
