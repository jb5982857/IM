package com.nanshan.we12talk.common.http;

import com.nanshan.we12talk.module.BaseModel;
import com.nanshan.we12talk.common.web.request.LoginEntity;
import com.nanshan.we12talk.common.web.request.RegisterEntity;
import com.nanshan.we12talk.common.web.response.LoginResponseEntity;

import rx.Observable;


/**
 * Created by jiangbo on 2018/9/23.
 * 所有接口调用都是从通过在这个类里添加
 * 内部类再区分
 * 然后不同的model层实现
 * 里面再用内部类区分登录、注册等等
 */

public class ApiContract {
    //注册相关
    public interface Code extends BaseModel {
        //注册
        Observable<Object> register(RegisterEntity entity);
    }

    //登录相关
    public interface Login extends BaseModel {
        //登录
        Observable<LoginResponseEntity> login(LoginEntity entity);
    }
}
