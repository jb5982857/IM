package com.nanshan.we12talk.module.chat.space;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.module.BaseFragment;
import com.nanshan.we12talk.module.BasePresenter;

/**
 * Created by jiangbo on 2018/9/27.
 * 朋友圈
 */

public class SpaceFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtils.d("onCreateView");
        return inflater.inflate(R.layout.space_fragment_layout, container, false);
    }

    @Override
    public BasePresenter onCreatePresenter() {
        return null;
    }

    @Override
    public void loadData() {
        LogUtils.d("loadData");
    }
}
