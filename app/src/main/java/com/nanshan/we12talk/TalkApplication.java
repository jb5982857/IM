package com.nanshan.we12talk;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.we12talk.constants.Key;
import com.nanshan.we12talk.common.db.DaoMaster;
import com.nanshan.we12talk.common.db.DaoSession;

/**
 * Created by jiangbo on 2018/8/23.
 * 自定义application
 */

public class TalkApplication extends Application {
    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtils.init("ITalk");
        initGreendao();
    }

    private DaoSession mDaoSession;

    private void initGreendao() {
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(getApplicationContext(), Key.DB_NAME);
        SQLiteDatabase database = openHelper.getWritableDatabase();
        DaoMaster master = new DaoMaster(database);
        mDaoSession = master.newSession();
    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }

}
