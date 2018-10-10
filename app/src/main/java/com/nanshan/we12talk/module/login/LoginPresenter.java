package com.nanshan.we12talk.module.login;

import android.content.Intent;

import com.im2.common.protobuf.MessageTemplate;
import com.nanshan.netty.Constants;
import com.nanshan.netty.WebSocketServer;
import com.nanshan.support.utils.LogUtils;
import com.nanshan.support.utils.ServiceUtils;
import com.nanshan.support.utils.StringUtils;
import com.nanshan.we12talk.module.BasePresenter;
import com.nanshan.we12talk.common.subscriber.SimpleSubscriber;
import com.nanshan.we12talk.common.web.request.LoginEntity;
import com.nanshan.we12talk.common.web.response.LoginResponseEntity;

import rx.Subscription;

/**
 * Created by jiangbo on 2018/9/6.
 * 登录的presenter
 */

public class LoginPresenter extends BasePresenter<LoginActivity, LoginModel> {

    public LoginPresenter(LoginActivity loginActivity) {
        super(loginActivity, new LoginModel());
    }

    //自动登录
    public void autoLogin(String username, String password) {
        LogUtils.i("auto login");
        //login(username, password);
        if (username == null) {
            return;
        }
        sendAccountConnect("7c662b2-9650-4e96-841f-9100799728a2", "15281674841");
    }

    public void login(String username, String password) {
        if (StringUtils.isEmpty(username, password)) {
            LogUtils.w("login but username is null!");
            return;
        }

        LoginEntity entity = new LoginEntity("password", username, password);
        Subscription subscription = mModel.login(entity).subscribe(new SimpleSubscriber<LoginResponseEntity>() {
            @Override
            public void call(LoginResponseEntity loginResponseEntity) {
                LogUtils.i("login response :" + loginResponseEntity.toString());
            }
        });
        compositeSubscription.add(subscription);
    }

    //进行长链接
    public void startConnect() {
        startService(WebSocketServer.WHAT_OPEN);
    }

    //进行发送用户长链接
    //"87c662b2-9650-4e96-841f-9100799728a2"
    //"15281674841"
    public void sendAccountConnect(String token, String username) {
        MessageTemplate.Client msg = MessageTemplate.Client.newBuilder().setToken(token)
                .setAccount(MessageTemplate.Account.newBuilder().setUsername(username).build()).build();
        startService(WebSocketServer.WHAT_SEND, msg);
    }

    //启动service
    private void startService(int type) {
        startService(type, null);
    }

    private void startService(int type, MessageTemplate.Client msg) {
        Intent intent = new Intent(mActivity, WebSocketServer.class);
        intent.putExtra(Constants.Key.TYPE, type);
        intent.putExtra(Constants.Key.MESSAGE, msg);
        ServiceUtils.safetyStartService(mActivity, intent);
    }
}
