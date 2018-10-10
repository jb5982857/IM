package com.nanshan.sms;

/**
 * Created by jiangbo on 2018/8/24.
 * 获取验证码和手机号码的回调
 */

public interface ISMSCallback {
    void onSuccess(String phoneNum);

    void onFailure(String phoneNum);
}
