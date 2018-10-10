package com.nanshan.we12talk.common.web.request;

/**
 * Created by jiangbo on 2018/9/13.
 * 请求注册的实体类
 */

public class RegisterEntity{
    private String username;
    private String password;
    private String phone;
    private String verifyCode;

    public RegisterEntity(String username, String password, String phone, String verifyCode) {
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.verifyCode = verifyCode;
    }
}
