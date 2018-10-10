package com.nanshan.support.utils;

import android.util.Log;

import java.net.HttpURLConnection;

/**
 * Created by jiangbo on 2017/12/24.
 * 日志类
 */

public class LogUtils {
    private static String tag;
    public static boolean allowV = true;
    public static boolean allowD = true;
    public static boolean allowI = true;
    public static boolean allowW = true;
    public static boolean allowE = true;
    public static boolean allowWtf = true;

    private LogUtils() {
    }

    public static void init(String tag) {
        LogUtils.tag = tag;
    }

    private static String generateTag() {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[4];
        String tag = "%s.%s(L:%d)";
        String callerClazzName = caller.getClassName();
        callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
        tag = String.format(tag, new Object[]{callerClazzName, caller.getMethodName(), Integer.valueOf(caller.getLineNumber())});
        if (LogUtils.tag != null) {
            return LogUtils.tag + " " + tag;
        }
        return tag;
    }

    public static void d(String content) {
        if (allowD) {
            String tag = generateTag();
            Log.d(tag, content);
        }
    }

    public static void d(String content, Throwable tr) {
        if (allowD) {
            String tag = generateTag();
            Log.d(tag, content, tr);
        }
    }

    public static void e(String content) {
        if (allowE) {
            String tag = generateTag();
            Log.e(tag, content);
        }
    }

    public static void e(String content, Throwable tr) {
        if (allowE) {
            String tag = generateTag();
            Log.e(tag, content, tr);
        }
    }

    public static void i(String content) {
        if (allowI) {
            String tag = generateTag();
            Log.i(tag, content);
        }
    }

    public static void i(String content, Throwable tr) {
        if (allowI) {
            String tag = generateTag();
            Log.i(tag, content, tr);
        }
    }

    public static void v(String content) {
        if (allowV) {
            String tag = generateTag();
            Log.v(tag, content);
        }
    }

    public static void v(String content, Throwable tr) {
        if (allowV) {
            String tag = generateTag();
            Log.v(tag, content, tr);
        }
    }

    public static void w(String content) {
        if (allowW) {
            String tag = generateTag();
            Log.w(tag, content);
        }
    }

    public static void w(String content, Throwable tr) {
        if (allowW) {
            String tag = generateTag();
            Log.w(tag, content, tr);
        }
    }

    public static void w(Throwable tr) {
        if (allowW) {
            String tag = generateTag();
            Log.w(tag, tr);
        }
    }

    public static void wtf(String content) {
        if (allowWtf) {
            String tag = generateTag();
            Log.wtf(tag, content);
        }
    }

    public static void wtf(String content, Throwable tr) {
        if (allowWtf) {
            String tag = generateTag();
            Log.wtf(tag, content, tr);
        }
    }
}
