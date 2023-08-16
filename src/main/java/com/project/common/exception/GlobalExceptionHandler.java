package com.project.common.exception;

import com.project.common.api.CommonResult;
import com.project.common.api.ResultCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理
 *
 * @author Qing2514
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 参数不合法异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public CommonResult<Object> handle(IllegalArgumentException e) {
        log.error(e.getMessage());
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 自定义校验异常
     */
    @ExceptionHandler(value = ApiException.class)
    public CommonResult<Object> handle(ApiException e) {
        log.error(e.getMessage());
        if (e.getErrorCode() != null) {
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public CommonResult<Object> handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request) {
        String uri = request.getRequestURI();
        log.error("请求地址'{}', 权限校验失败'{}'", uri, e.getMessage());
        return CommonResult.failed(ResultCode.FORBIDDEN);
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
