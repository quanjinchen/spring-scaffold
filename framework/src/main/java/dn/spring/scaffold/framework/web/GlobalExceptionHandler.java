package dn.spring.scaffold.framework.web;

import dn.spring.scaffold.common.exception.BizException;
import dn.spring.scaffold.common.constant.ResultCode;
import dn.spring.scaffold.common.pojo.RespInfo;
import dn.spring.scaffold.common.utils.JsonUtils;
import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.exception.SaTokenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Iterator;
import java.util.List;

/**
 * 全局统一异常处理
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public RespInfo<Void> handleException(Exception exception) {
        RespInfo<Void> respInfo = toRespInfo(exception);
        log.info("response: {}", JsonUtils.toJson(respInfo));
        return respInfo;
    }

    private RespInfo<Void> toRespInfo(Exception exception) {
        if (exception instanceof BizException) {
            BizException bizException = (BizException) exception;
            log.warn("biz exception: {}", bizException.getMessage(), bizException);
            return new RespInfo<Void>(bizException.getCode(), bizException.getMsg(), null);
        }

        if (exception instanceof HttpRequestMethodNotSupportedException) {
            log.warn("http method not supported: {}", exception.getMessage());
            return RespInfo.failed(ResultCode.METHOD_NOT_ALLOWED, "invalid request method");
        }

        if (exception instanceof HttpMessageNotReadableException) {
            log.warn("request parse failed: {}", exception.getMessage(), exception);
            return RespInfo.failed(ResultCode.BAD_REQUEST, "request parse failed");
        }

        if (exception instanceof NoHandlerFoundException) {
            log.warn("no handler found: {}", exception.getMessage());
            return RespInfo.failed(ResultCode.NOT_FOUND, "invalid request path");
        }

        if (exception instanceof MissingServletRequestParameterException) {
            MissingServletRequestParameterException missingServletRequestParameterException = (MissingServletRequestParameterException) exception;
            log.warn("missing request parameter: {}", missingServletRequestParameterException.getParameterName(), exception);
            return RespInfo.failed(ResultCode.BAD_REQUEST, missingServletRequestParameterException.getParameterName() + " is required");
        }

        if (exception instanceof BindException) {
            BindException bindException = (BindException) exception;
            log.warn("bind exception: {}", bindException.getMessage(), bindException);
            String message = getValidExceptionMsg(bindException.getAllErrors());
            return RespInfo.failed(ResultCode.BAD_REQUEST, StringUtils.hasText(message) ? message : ResultCode.BAD_REQUEST.getMessage());
        }

        if (exception instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) exception;
            log.warn("method argument not valid: {}", methodArgumentNotValidException.getMessage(), methodArgumentNotValidException);
            String message = getValidExceptionMsg(methodArgumentNotValidException.getBindingResult().getAllErrors());
            return RespInfo.failed(ResultCode.BAD_REQUEST, StringUtils.hasText(message) ? message : ResultCode.BAD_REQUEST.getMessage());
        }

        if (exception instanceof ConstraintViolationException) {
            ConstraintViolationException constraintViolationException = (ConstraintViolationException) exception;
            log.warn("constraint violation: {}", constraintViolationException.getMessage(), constraintViolationException);
            Iterator<ConstraintViolation<?>> iterator = constraintViolationException.getConstraintViolations().iterator();
            String message = iterator.hasNext() ? iterator.next().getMessage() : ResultCode.BAD_REQUEST.getMessage();
            return RespInfo.failed(ResultCode.BAD_REQUEST, message);
        }

        if (exception instanceof MethodArgumentTypeMismatchException) {
            MethodArgumentTypeMismatchException methodArgumentTypeMismatchException = (MethodArgumentTypeMismatchException) exception;
            log.warn("method argument type mismatch: {}", methodArgumentTypeMismatchException.getMessage(), methodArgumentTypeMismatchException);
            return RespInfo.failed(ResultCode.BAD_REQUEST, methodArgumentTypeMismatchException.getName() + " type mismatch");
        }

        if (exception instanceof NotLoginException) {
            log.warn("not login: {}", exception.getMessage());
            return RespInfo.failed(ResultCode.UNAUTHORIZED);
        }

        if (exception instanceof NotPermissionException) {
            NotPermissionException notPermissionException = (NotPermissionException) exception;
            log.warn("not permission: {}", notPermissionException.getPermission());
            return RespInfo.failed(ResultCode.FORBIDDEN);
        }

        if (exception instanceof NotRoleException) {
            NotRoleException notRoleException = (NotRoleException) exception;
            log.warn("not role: {}", notRoleException.getRole());
            return RespInfo.failed(ResultCode.FORBIDDEN);
        }

        if (exception instanceof SaTokenException) {
            log.warn("sa token exception: {}", exception.getMessage(), exception);
            return RespInfo.failed(ResultCode.UNAUTHORIZED);
        }

        log.error("unexpected exception: {}", exception.getMessage(), exception);
        return RespInfo.failed(ResultCode.INTERNAL_SERVER_ERROR);
    }

    private String getValidExceptionMsg(List<ObjectError> errors) {
        if (errors == null || errors.isEmpty()) {
            return null;
        }

        for (ObjectError error : errors) {
            if (error instanceof FieldError) {
                return error.getDefaultMessage();
            }
        }

        return errors.get(0).getDefaultMessage();
    }
}
