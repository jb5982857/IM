package com.nanshan.we12talk.module.Register;

import com.nanshan.sms.ISMSCallback;
import com.nanshan.sms.SMSManager;
import com.nanshan.support.utils.MobileUtils;
import com.nanshan.support.utils.ToastUtils;
import com.nanshan.we12talk.module.BaseModel;
import com.nanshan.we12talk.module.BasePresenter;
import com.nanshan.we12talk.R;

/**
 * Created by jiangbo on 2018/9/13.
 * 输入手机号界面
 */

public class PhoneNumPresenter extends BasePresenter<PhoneNumActivity, BaseModel> {
    public PhoneNumPresenter(PhoneNumActivity phoneNumActivity) {
        super(phoneNumActivity, null);
        SMSManager.getInstance().init(phoneNumActivity);
    }

    public void getPhoneCode(String phone) {
        if (!MobileUtils.isMobileNO(phone)) {
            ToastUtils.show(mActivity, mActivity.getString(R.string.error_phone));
            return;
        }
        SMSManager.getInstance().getCode("86", phone, new ISMSCallback() {
            @Override
            public void onSuccess(String phoneNum) {
                mActivity.sendSuccess(phoneNum);
            }

            @Override
            public void onFailure(String phoneNum) {
                ToastUtils.show(mActivity, mActivity.getString(R.string.error_send_phone));
            }
        });
    }

    public void onDestroy() {
        SMSManager.getInstance().onDestroy();
    }
}
