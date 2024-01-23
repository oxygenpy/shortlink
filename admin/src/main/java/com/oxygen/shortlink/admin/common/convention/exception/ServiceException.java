package com.oxygen.shortlink.admin.common.convention.exception;

import com.oxygen.shortlink.admin.common.convention.errorcode.BaseErrorCode;
import com.oxygen.shortlink.admin.common.convention.errorcode.IErrorCode;

import java.util.Optional;

/**
 * @author LiJinLong
 * @description 服务端异常
 * @create 2024-01-22 20:42
 * @date 1.0
 */
public class ServiceException extends AbstractException {

    public ServiceException(String message) {
        this(message, null, BaseErrorCode.SERVICE_ERROR);
    }

    public ServiceException(IErrorCode errorCode) {
        this(null, errorCode);
    }

    public ServiceException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ServiceException(String message, Throwable throwable, IErrorCode errorCode) {
        super(Optional.ofNullable(message).orElse(errorCode.message()), throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ServiceException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}