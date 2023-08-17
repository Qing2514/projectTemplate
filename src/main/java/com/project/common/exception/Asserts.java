package com.project.common.exception;


import com.project.common.api.IErrorCode;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 断言处理类，用于抛出各种API异常
 *
 * @author Qing2514
 */
public class Asserts extends Assert {

    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }

    public static void notNull(@Nullable Object object, IErrorCode errorCode) {
        if (object == null) {
            throw new ApiException(errorCode);
        }
    }

    public static void isNull(@Nullable Object object, IErrorCode errorCode) {
        if (object != null) {
            throw new ApiException(errorCode);
        }
    }

    public static void notEmpty(@Nullable List<?> array, IErrorCode errorCode) {
        if (ObjectUtils.isEmpty(array)) {
            throw new ApiException(errorCode);
        }
    }

    public static void isEmpty(@Nullable List<?> array, IErrorCode errorCode) {
        if (!ObjectUtils.isEmpty(array)) {
            throw new ApiException(errorCode);
        }
    }

    public static void notTrue(boolean expression, IErrorCode errorCode) {
        if (expression) {
            throw new ApiException(errorCode);
        }
    }

    public static void isTrue(boolean expression, IErrorCode errorCode) {
        if (!expression) {
            throw new ApiException(errorCode);
        }
    }

}
