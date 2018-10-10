package com.nanshan.we12talk.common.subscriber;

import android.content.Context;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.support.utils.ToastUtils;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.common.exception.APIException;
import com.nanshan.we12talk.common.utils.ActivityManager;
import com.nanshan.we12talk.common.view.ProgressDialog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;
import rx.Subscriber;

/**
 * Created by jiangbo on 2018/9/23.
 * http的基础监听
 */

public abstract class SimpleSubscriber<T> extends Subscriber<T> {
    protected Context mContext;

    public SimpleSubscriber() {
        mContext = ActivityManager.getInstance().getTopActivity().getApplicationContext();
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtils.i("SimpleSubscriber onStart");
    }

    @Override
    public void onCompleted() {
        LogUtils.i("http complete");
    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof APIException) {
            LogUtils.e("http error " + e.getMessage());
            ToastUtils.show(mContext, ((APIException) e).getDescrible());
        } else if (e instanceof UnknownHostException) {
            ToastUtils.show(mContext, mContext.getString(R.string.unknown_host));
        } else if (e instanceof SocketTimeoutException || e instanceof HttpException) {
            ToastUtils.show(mContext, mContext.getString(R.string.http_time_out));
        } else if (e instanceof ConnectException) {
            ToastUtils.show(mContext, mContext.getString(R.string.http_connect_failed));
        } else {
            ToastUtils.show(mContext, mContext.getString(R.string.http_error));
        }
        e.printStackTrace();
    }

    @Override
    public void onNext(T t) {
        call(t);
    }

    public abstract void call(T t);
}
