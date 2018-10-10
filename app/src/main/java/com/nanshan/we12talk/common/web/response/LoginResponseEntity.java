package com.nanshan.we12talk.common.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by jiangbo on 2018/9/23.
 * 登录返回的data实体类
 */

public class LoginResponseEntity {
    @SerializedName("assess_token")
    @Expose
    private String assessToken;

    @SerializedName("token_type")
    @Expose
    private String tokenType;

    @SerializedName("refresh_token")
    @Expose
    private String refreshToken;

    @SerializedName("expires_in")
    @Expose
    private int expiresIn;

    @SerializedName("scope")
    @Expose
    private String scope;

    public String getAssessToken() {
        return assessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public int getExpiresIn() {
        return expiresIn;
    }

    public String getScope() {
        return scope;
    }

    @Override
    public String toString() {
        return "LoginResponseEntity{" +
                "assessToken='" + assessToken + '\'' +
                ", tokenType='" + tokenType + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", expiresIn=" + expiresIn +
                ", scope='" + scope + '\'' +
                '}';
    }
}
