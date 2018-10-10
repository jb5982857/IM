package com.nanshan.we12talk.common.subscriber;

import android.app.Activity;

import com.nanshan.we12talk.common.utils.ActivityManager;
import com.nanshan.we12talk.common.view.ProgressDialog;

/**
 * Created by jiangbo on 2018/9/25.
 * 带有loading请求的http
 */

public abstract class ProgressSubscriber<T> extends SimpleSubscriber<T> {
    private ProgressDialog mDialog;
    private Activity mTopActivity;

    public ProgressSubscriber() {
        super();
        mTopActivity = ActivityManager.getInstance().getTopActivity();
        mDialog = new ProgressDialog(mTopActivity);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mTopActivity.isFinishing()) {
            return;
        }
        mDialog.show();
        mDialog.setLoadingText("正在加载中......");
    }

    @Override
    public void onCompleted() {
        super.onCompleted();
        dismissDialog();
    }

    @Override
    public void onError(Throwable e) {
        super.onError(e);
        dismissDialog();
    }

    private void dismissDialog() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
