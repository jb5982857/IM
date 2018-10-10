package com.nanshan.we12talk.common.web.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jiangbo on 2018/9/19.
 * 登录请求的实体类
 */

public class LoginEntity {
    @SerializedName("grant_type")
    @Expose
    private String grantType;
    private String username;
    private String password;

    public LoginEntity(String grantType, String username, String password) {
        this.grantType = grantType;
        this.username = username;
        this.password = password;
    }
}
