package com.nanshan.we12talk.module.Register;

import com.nanshan.support.utils.LogUtils;
import com.nanshan.support.utils.StringUtils;
import com.nanshan.support.utils.ToastUtils;
import com.nanshan.we12talk.common.subscriber.ProgressSubscriber;
import com.nanshan.we12talk.module.BasePresenter;
import com.nanshan.we12talk.R;
import com.nanshan.we12talk.common.db.entity.Account;
import com.nanshan.we12talk.common.subscriber.SimpleSubscriber;
import com.nanshan.we12talk.common.utils.DBManager;
import com.nanshan.we12talk.common.web.request.RegisterEntity;


import java.sql.Date;

import rx.Subscription;

/**
 * Created by jiangbo on 2018/9/13.
 * 获取验证码的接口
 */

public class CodePresenter extends BasePresenter<CodeActivity, CodeModel> {
    public CodePresenter(CodeActivity codeActivity) {
        super(codeActivity, new CodeModel());
    }

    public void register(final String phone, final String password, final String code) {
        LogUtils.i("phone:" + phone + " password:" + password + " code:" + code);
        if (StringUtils.isEmpty(phone, password, code)) {
            ToastUtils.show(mActivity, mActivity.getString(R.string.empty_params));
            return;
        }

        RegisterEntity entity = new RegisterEntity(phone, password, phone, code);
        Subscription subscription = mModel.register(entity).subscribe(new ProgressSubscriber<Object>() {
            @Override
            public void call(Object o) {
                ToastUtils.show(mActivity, mActivity.getString(R.string.register_success));
                Account account = new Account();
                account.setPhone(true);
                account.setUsername(phone);
                account.setUpdateTime(new Date(System.currentTimeMillis()));
                DBManager.getInstance().saveOrUpdateAccount(account);
                mActivity.onSuccess(account.getUsername(), password);
            }
        });
        compositeSubscription.add(subscription);
    }
}
