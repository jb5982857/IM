package com.nanshan.we12talk.module.Register;

import com.nanshan.support.http.HttpUtils;
import com.nanshan.we12talk.constants.H;
import com.nanshan.we12talk.common.http.ApiContract;
import com.nanshan.we12talk.common.http.ApiServer;
import com.nanshan.we12talk.common.http.ApiFilteredFactory;
import com.nanshan.we12talk.common.web.request.RegisterEntity;

import rx.Observable;

/**
 * Created by jiangbo on 2018/9/13.
 * 手机注册的http请求
 */

public class CodeModel implements ApiContract.Code {

    @Override
    public Observable<Object> register(RegisterEntity entity) {
        return ApiFilteredFactory.compose(HttpUtils.getInstance().create(H.BASE).create(ApiServer.class).register(entity));
    }
}
