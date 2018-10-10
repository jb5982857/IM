package com.nanshan.we12talk.common.exception;

/**
 * Created by jiangbo on 2018/9/23.
 * 网络请求，请求成功，但是code不是SUCCESS时抛出的异常
 */

public class APIException extends RuntimeException {
    private int code;
    private String describle;
    private String message;

    public APIException(int code, String describle, String message) {
        this.code = code;
        this.describle = describle;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getDescrible() {
        return describle;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
