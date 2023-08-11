package com.project.common.exception;

import com.project.common.api.CommonResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author Qing2514
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonResult<Object> handle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(value = ApiException.class)
    public CommonResult<Object> handle(ApiException e) {
        log.error(e.getMessage());
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public CommonResult<Object> handleValidException(MethodArgumentNotValidException e) {
        return getCommonResult(e);
    }

    @ExceptionHandler(value = BindException.class)
    public CommonResult<Object> handleValidException(BindException e) {
        return getCommonResult(e);
    }

    private CommonResult<Object> getCommonResult(BindException e) {
        BindingResult bindingResult = e.getBindingResult();
        String message = null;
        if (bindingResult.hasErrors()) {
            FieldError fieldError = bindingResult.getFieldError();
            if (fieldError != null) {
                message = fieldError.getField() + fieldError.getDefaultMessage();
            }
        }
        return CommonResult.validateFailed(message);
    }

}
