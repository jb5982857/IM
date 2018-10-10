package com.nanshan.support.utils;

import android.content.Context;
import android.graphics.Color;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.nanshan.support.R;
import com.nanshan.support.drawable.RoundCorner;

/**
 * Created by jiangbo on 2018/8/23.
 * toast工具类
 */

public class ToastUtils {
    private static android.widget.Toast mToast;
    private static TextView mView;

    public static void show(Context context, String msg) {
        if (mToast == null) {
            int padding = ScreenUtils.getScreenWidth(context) * 35 / 600;
            mToast = Toast.makeText(context, msg, android.widget.Toast.LENGTH_SHORT);
            mToast.setGravity(Gravity.CENTER, 0, 0);

            mView = new TextView(context);
            mView.setTextColor(Color.WHITE);
            mView.setBackgroundDrawable(new RoundCorner(context, context.getResources().getColor(R.color.color_toast_bg)));

            mView.setPadding(padding, padding, padding, padding);
            mToast.setView(mView);
        }
        mView.setText(msg);
        mToast.show();
    }

    private static android.widget.Toast mToastExt;
    private static TextView mViewExt;

    public static void showLong(Context context, String msg) {
        if (mToastExt == null) {
            int padding = ScreenUtils.getScreenWidth(context) * 35 / 600;
            mToastExt = android.widget.Toast.makeText(context, msg, Toast.LENGTH_LONG);
            mToastExt.setGravity(Gravity.CENTER, 0, 0);

            mViewExt = new TextView(context);
            mViewExt.setTextColor(Color.WHITE);
            mViewExt.setTextSize(TypedValue.COMPLEX_UNIT_PX, padding * 3 / 4);
            mViewExt.setBackgroundDrawable(new RoundCorner(context, context.getResources().getColor(R.color.color_toast_bg)));

            mViewExt.setPadding(padding, padding, padding, padding);
            mToastExt.setView(mViewExt);
        }
        mViewExt.setText(msg);
        mToastExt.show();
    }
}
