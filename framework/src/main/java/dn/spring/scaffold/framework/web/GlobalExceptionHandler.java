package dn.spring.scaffold.framework.web;

import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BizException.class)
    public RespInfo<Void> handleBizException(BizException exception) {
        return new RespInfo<>(exception.getCode(), exception.getMessage(), null);
    }

    @ExceptionHandler(SaTokenException.class)
    public RespInfo<Void> handleSaTokenException(SaTokenException exception) {
        return RespInfo.failed(ResultCode.UNAUTHORIZED, exception.getMessage());
    }

    @ExceptionHandler(NotLoginException.class)
    public RespInfo<Void> handleNotLoginException(NotLoginException exception) {
        return RespInfo.failed(ResultCode.NOT_LOGGED_IN, exception.getMessage());
    }

    @ExceptionHandler(NotPermissionException.class)
    public RespInfo<Void> handleNotPermissionException(NotPermissionException exception) {
        return RespInfo.failed(ResultCode.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(NotRoleException.class)
    public RespInfo<Void> handleNotRoleException(NotRoleException exception) {
        return RespInfo.failed(ResultCode.FORBIDDEN, exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RespInfo<Void> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return RespInfo.failed(ResultCode.BAD_REQUEST, fieldError == null ? ResultCode.BAD_REQUEST.getMessage() : fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    public RespInfo<Void> handleBindException(BindException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        return RespInfo.failed(ResultCode.BAD_REQUEST, fieldError == null ? ResultCode.BAD_REQUEST.getMessage() : fieldError.getDefaultMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public RespInfo<Void> handleConstraintViolationException(ConstraintViolationException exception) {
        Iterator<ConstraintViolation<?>> iterator = exception.getConstraintViolations().iterator();
        String message = iterator.hasNext() ? iterator.next().getMessage() : ResultCode.BAD_REQUEST.getMessage();
        return RespInfo.failed(ResultCode.BAD_REQUEST, message);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public RespInfo<Void> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        return RespInfo.failed(ResultCode.BAD_REQUEST, exception.getParameterName() + " is required");
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public RespInfo<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException exception) {
        return RespInfo.failed(ResultCode.BAD_REQUEST, exception.getName() + " type mismatch");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public RespInfo<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException exception) {
        Throwable cause = exception.getMostSpecificCause();
        return RespInfo.failed(ResultCode.BAD_REQUEST, cause == null ? exception.getMessage() : cause.getMessage());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public RespInfo<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception) {
        return RespInfo.failed(ResultCode.BAD_REQUEST, exception.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public RespInfo<Void> handleException(Exception exception) {
        return RespInfo.failed(exception.getMessage());
    }
}
