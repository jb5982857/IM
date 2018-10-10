package com.nanshan.we12talk.module;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.support.utils.ScreenUtils;
import com.nanshan.we12talk.R;

import java.lang.annotation.Repeatable;

import butterknife.BindView;

/**
 * Created by jiangbo on 2018/9/13.
 * 有toolbar的activity需要继承这个
 */

public abstract class BaseToolBarActivity<T extends BasePresenter> extends BaseActivity<T> {

    @Nullable
    @BindView(R.id.toolbar)
    protected Toolbar mToolbar;
    @Nullable
    @BindView(R.id.fab)
    protected FloatingActionButton mFab;
    @Nullable
    @BindView(R.id.toolbar_title)
    protected TextView mTitle;

    protected int navigationWidth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initToolbar();
        initFab();
        setTitle(onCreateTitle());
        navigationWidth = ScreenUtils.getScreenWidth(this) / 10;
        if (getNavigationIcon() != null && mToolbar != null) {
            mToolbar.setNavigationIcon(getNavigationIcon());
        }
    }

    //toolbar的title
    public abstract String onCreateTitle();

    //设置toolbar返回键的icon
    public Drawable getNavigationIcon() {
        return null;
    }

    //初始化ToolBar，并不是所有的界面都有toolbar
    private void initToolbar() {
        if (mToolbar == null) {
            return;
        }
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initFab() {
        if (mFab == null) {
            return;
        }
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    //设置title文字，但这个方法必须在ButterKnife.bind之后调用，不然会找不到mTitle
    protected void setTitle(String title) {
        if (mTitle == null || TextUtils.isEmpty(title)) {
            LogUtils.e("Toolbar's title is not init or text title is null");
            return;
        }
        mTitle.setText(title);
    }
}
