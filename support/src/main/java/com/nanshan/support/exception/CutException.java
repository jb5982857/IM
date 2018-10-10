package com.nanshan.support.exception;

/**
 * Created by jiangbo on 2018/8/28.
 */

public class CutException extends RuntimeException {
    public CutException(String message) {
        super(message);
    }

    public CutException(String message, Throwable cause) {
        super(message, cause);
    }
}
