package com.nanshan.we12talk.common.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.TextView;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.we12talk.R;

/**
 * Created by jiangbo on 2018/9/25.
 * 用于http请求时的提示加载dialog
 */

public class ProgressDialog extends Dialog {

    public ProgressDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtils.d("ProgressDialog onCreate");
        setContentView(R.layout.progress_dialog);
        setCancelable(false);
    }

    //设置loading字样，这个方法必须在show()方法之后调用
    public void setLoadingText(String text) {
        LogUtils.d("ProgressDialog setLoadingText");
        ((TextView) findViewById(R.id.tv_notice)).setText(text);
    }
}
