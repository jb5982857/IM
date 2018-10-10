package com.nanshan.we12talk.module.login;

import com.nanshan.support.http.HttpUtils;
import com.nanshan.we12talk.constants.H;
import com.nanshan.we12talk.common.http.ApiContract;
import com.nanshan.we12talk.common.http.ApiServer;
import com.nanshan.we12talk.common.http.ApiFilteredFactory;
import com.nanshan.we12talk.common.web.request.LoginEntity;
import com.nanshan.we12talk.common.web.response.LoginResponseEntity;

import rx.Observable;

/**
 * Created by jiangbo on 2018/9/13.
 * 登录接口
 */

public class LoginModel implements ApiContract.Login {

    @Override
    public Observable<LoginResponseEntity> login(LoginEntity entity) {
        return ApiFilteredFactory.compose(HttpUtils.getInstance().create(H.BASE).create(ApiServer.class).login(entity));
    }
}
