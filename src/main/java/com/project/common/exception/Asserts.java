package com.project.common.exception;


import com.project.common.api.IErrorCode;
import org.springframework.util.Assert;

/**
 * 断言处理类，用于抛出各种API异常
 */
public class Asserts extends Assert {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

}
