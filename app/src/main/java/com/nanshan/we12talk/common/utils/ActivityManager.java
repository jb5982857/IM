package com.nanshan.we12talk.common.utils;

import android.app.Activity;

import com.nanshan.support.utils.LogUtils;

import java.lang.ref.WeakReference;
import java.util.Stack;

/**
 * Created by jiangbo on 2018/9/19.
 * activity管理器
 */

public class ActivityManager {
    //保存activity的队列，弱引用，不影响activity被回收
    private Stack<WeakReference<Activity>> mStack = new Stack<>();

    private static class ManagerImpl {
        private static final ActivityManager mInstance = new ActivityManager();
    }

    public static ActivityManager getInstance() {
        return ManagerImpl.mInstance;
    }

    //设置activity，需要在每个activity的onCreate中调用
    public void setActivity(Activity activity) {
        mStack.push(new WeakReference<>(activity));
    }

    //获取栈顶的activity
    public Activity getTopActivity() {
        Activity activity;
        if (isEmptyActivity()) {
            LogUtils.e("no activity in stack");
            return null;
        }

        while ((activity = mStack.peek().get()) == null) {
            mStack.pop();
        }
        return activity;
    }

    //删除activity
    public void onDestroy(Activity activity) {
        for (WeakReference<Activity> weakActivity : mStack) {
            if (activity.equals(weakActivity.get())) {
                mStack.remove(weakActivity);
                return;
            }
        }
    }

    private boolean isEmptyActivity() {
        if (mStack.isEmpty()) {
            LogUtils.i("there is no acitivity in the stack");
            return true;
        }

        for (WeakReference<Activity> activity : mStack) {
            if (activity.get() != null) {
                LogUtils.i("there are activities in stack");
                return false;
            }
        }
        LogUtils.i("there are no activity in weak");
        return true;
    }
}
