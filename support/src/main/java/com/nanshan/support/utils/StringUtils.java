package com.nanshan.support.utils;

import android.text.TextUtils;

/**
 * Created by jiangbo on 2018/9/13.
 * 字符串工具类
 */

public class StringUtils {
    /**
     * 判断字符串是否为空
     * @param strs
     * @return
     */
    public static boolean isEmpty(String... strs) {
        if (strs == null) {
            return true;
        }
        for (String str : strs) {
            if (TextUtils.isEmpty(str)) {
                return true;
            }
        }
        return false;
    }
}
