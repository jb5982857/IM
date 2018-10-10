package com.nanshan.we12talk.common.http;

import com.nanshan.we12talk.common.web.request.LoginEntity;
import com.nanshan.we12talk.common.web.request.RegisterEntity;
import com.nanshan.we12talk.common.web.response.BaseResponseEntity;
import com.nanshan.we12talk.common.web.response.LoginResponseEntity;

import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jiangbo on 2018/9/23.
 * 这个类实现了http中所有的retrofit注解方法
 * 每添加一个接口调用，需要在这个类中加一个方法，为了方便统一和retrofit.create()方法的调用
 */

public interface ApiServer {
    //注册接口
    @Headers({"Content-type:application/json"})
    @POST("user")
    Observable<BaseResponseEntity<Object>> register(@Body RegisterEntity register);

    //登录接口
    @Headers({"Content-type:application/json"})
    @POST("user")
    Observable<BaseResponseEntity<LoginResponseEntity>> login(@Body LoginEntity register);
}
