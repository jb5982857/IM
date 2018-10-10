package com.nanshan.we12talk.common.web.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.nanshan.we12talk.constants.Code;

/**
 * Created by jiangbo on 2018/9/15.
 * 返回的基本对象
 */

public class BaseResponseEntity<T> {
    @SerializedName("code")
    @Expose
    //返回code，0成功
    private int code = -1;
    @SerializedName("describe")
    @Expose
    //用户打toast给用户看
    private String describe;
    @SerializedName("message")
    @Expose
    //用于打日志，好检查问题
    private String message;

    @SerializedName("data")
    @Expose
    private T data;

    public BaseResponseEntity() {
    }

    public int getCode() {
        return code;
    }

    public String getDescribe() {
        return describe;
    }

    public String getMessage() {
        return message;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponseEntity{" +
                "code=" + code +
                ", describe='" + describe + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    public boolean isSuccess() {
        return code == Code.SUCCESS;
    }
}
