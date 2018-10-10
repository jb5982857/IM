package com.nanshan.sms;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

/**
 * Created by jiangbo on 2018/8/24.
 * 发送手机验证码的工具类
 */

public class SMSManager {
    private EventHandler eventHandler;
    private ISMSCallback mCallback;

    private static class SmsImp {
        private static SMSManager mInstance = new SMSManager();
    }

    public static SMSManager getInstance() {
        return SmsImp.mInstance;
    }

    public void init(Context context) {
        //Application的onCreate方法调用
        MobSDK.init(context);
        eventHandler = new EventHandler() {
            @Override
            public void afterEvent(int event, int result, Object data) {
                // afterEvent会在子线程被调用，因此如果后续有UI相关操作，需要将数据发送到UI线程
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                new Handler(Looper.getMainLooper(), new Handler.Callback() {
                    @Override
                    public boolean handleMessage(Message msg) {
                        int event = msg.arg1;
                        int result = msg.arg2;
                        Object data = msg.obj;
                        if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                            if (result == SMSSDK.RESULT_COMPLETE) {
                                // 处理成功得到验证码的结果
                                // 请注意，此时只是完成了发送验证码的请求，验证码短信还需要几秒钟之后才送达
                                if (mCallback != null) {
                                    mCallback.onSuccess(phoneNum);
                                }
                            } else {
                                // 处理错误的结果
                                ((Throwable) data).printStackTrace();
                                if (mCallback != null) {
                                    mCallback.onFailure(phoneNum);
                                }
                            }
                        }
                        return false;
                    }
                }).sendMessage(msg);
            }
        };
        // 注册一个事件回调，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eventHandler);
    }

    private String phoneNum;

    public void getCode(String country, String phoneNum, ISMSCallback callback) {
        mCallback = callback;
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        SMSSDK.getVerificationCode(country, phoneNum);
        this.phoneNum = phoneNum;
    }

    //这个方法暂时不调用，验证码可以发送至服务器调用
    @Deprecated
    public void commitCode(String country, String code, String phoneNum) {
        // 提交验证码，其中的code表示验证码，如“1357”
        SMSSDK.submitVerificationCode(country, phoneNum, code);
    }

    //在不用的时候，需要注销，不然可能会内存泄漏
    public void onDestroy() {
        SMSSDK.unregisterEventHandler(eventHandler);
    }
}
