package com.nanshan.support.utils;

import android.content.Context;
import android.content.Intent;

/**
 * Created by jiangbo on 2018/9/27.
 * service工具类
 */

public class ServiceUtils {
    public static void safetyStartService(Context context, Intent intent) {
        try {
            context.startService(intent);
        } catch (Exception e) {
            LogUtils.e("start service " + intent + " failed , it is maybe sdk 8.0 and start by background service");
        }
    }
}
