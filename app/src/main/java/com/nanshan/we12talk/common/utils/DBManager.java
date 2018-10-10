package com.nanshan.we12talk.common.utils;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.text.TextUtils;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.we12talk.common.db.AccountDao;
import com.nanshan.we12talk.common.db.DaoSession;
import com.nanshan.we12talk.common.db.entity.Account;

import java.util.List;

/**
 * Created by jiangbo on 2018/9/19.
 * db管理器，所有的db操作都在这个类中进行
 * 这个类的调用一定要在当前有显示activity的时候调用，不然session初始化就会失败
 * 增、删、改用handler异步去执行，查同步执行
 */

public class DBManager {
    //db操作线程
    private Handler mHandler;
    //操作db的session对象
    private DaoSession session = Tools.getDaoSession(ActivityManager.getInstance().getTopActivity());
    //操作用户db的对象
    private AccountDao accountDao = session.getAccountDao();
    //线程名称
    private static final String DB_THREAD = "db_thread";

    //这些常量标记这每次操作，理论上每次新增一个db操作就需要在这里加一个常量，暂时没有想到好的扩展方法
    private static final int SAVE_OR_UPDATE_ACCOUNT = 0;
    private static final int DELETE_ACCOUNT_BY_USERNAME = 1;

    private static class DBImpl {
        private static DBManager mInstance = new DBManager();
    }

    public static DBManager getInstance() {
        return DBImpl.mInstance;
    }

    private DBManager() {
        HandlerThread thread = new HandlerThread(DB_THREAD);
        thread.start();
        mHandler = new Handler(thread.getLooper(), new Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                dealMessage(msg);
                return false;
            }
        });
    }

    //保存账号操作，如果有则更新
    public void saveOrUpdateAccount(Account account) {
        if (account == null) {
            LogUtils.e("save but account is null!");
            return;
        }
        mHandler.obtainMessage(SAVE_OR_UPDATE_ACCOUNT, account).sendToTarget();
    }

    //通过username来删除账号
    public void deleteAccountByUsername(String username) {
        if (TextUtils.isEmpty(username)) {
            LogUtils.e("delete account but username is null!");
            return;
        }
        mHandler.obtainMessage(DELETE_ACCOUNT_BY_USERNAME, username).sendToTarget();
    }

    //查询第一条数据
    public Account getFirstAccount() {
        //根据时间去查询最新的有变动的账号
        List<Account> accounts = accountDao.queryBuilder().orderDesc(AccountDao.Properties.UpdateTime).list();
        return accounts == null || accounts.size() == 0 ? null : accounts.get(0);
    }

    //处理在db_thread线程中的消息，通过what来区分是什么操作
    private void dealMessage(Message msg) {
        if (session == null) {
            LogUtils.e("DaoSession is null");
            return;
        }
        switch (msg.what) {
            case SAVE_OR_UPDATE_ACCOUNT:
                Account saveAccount = (Account) msg.obj;
                List<Account> saveFounds = accountDao.queryBuilder().where(AccountDao.Properties
                        .Username.eq(saveAccount.getUsername())).list();
                if (saveFounds != null && saveFounds.size() != 0) {
                    LogUtils.i("find account " + saveFounds.get(0).getUsername() + " in db , update now!");
                    saveAccount.setId(saveFounds.get(0).getId());
                    accountDao.update(saveAccount);
                    break;
                }
                LogUtils.i("insert account " + saveAccount.getUsername() + " now");
                accountDao.insert(saveAccount);
                break;
            case DELETE_ACCOUNT_BY_USERNAME:
                List<Account> deleteFounds = accountDao.queryBuilder().where(AccountDao.Properties
                        .Username.eq(msg.obj)).list();
                if (deleteFounds == null || deleteFounds.size() == 0) {
                    LogUtils.e("delete " + msg.obj + " but do not find in db");
                    return;
                }
                LogUtils.i("delete " + msg.obj);
                accountDao.delete(deleteFounds.get(0));
                break;
            default:
        }
    }
}
