package com.nanshan.we12talk.common.utils;

import android.app.Activity;

import com.nanshan.we12talk.TalkApplication;
import com.nanshan.we12talk.common.db.DaoSession;

/**
 * Created by jiangbo on 2018/9/17.
 * 一些公共的工具类
 */

public class Tools {
    //获取DaoSession
    public static DaoSession getDaoSession(Activity activity) {
        if (activity == null) {
            return null;
        }
        return ((TalkApplication) activity.getApplication()).getDaoSession();
    }
}
