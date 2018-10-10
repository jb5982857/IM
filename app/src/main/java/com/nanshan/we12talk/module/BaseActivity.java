package com.nanshan.we12talk.module;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.nanshan.we12talk.common.utils.ActivityManager;

import butterknife.ButterKnife;

/**
 * Created by jiangbo on 2018/8/23.
 * 基础activity
 */

public abstract class BaseActivity<T extends BasePresenter> extends AppCompatActivity implements IView<T> {
    protected T mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //这个方法必须在setContentView方法之后调用
        ButterKnife.bind(this);
        mPresenter = onCreatePresenter();
        ActivityManager.getInstance().setActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
        ActivityManager.getInstance().onDestroy(this);
    }
}
