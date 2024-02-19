package com.oxygen.shortlink.project.common.convention.exception;


import com.oxygen.shortlink.project.common.convention.errorcode.BaseErrorCode;
import com.oxygen.shortlink.project.common.convention.errorcode.IErrorCode;

/**
 * @author LiJinLong
 * @description 客户端异常
 * @create 2024-01-22 20:41
 * @date 1.0
 */
public class ClientException extends AbstractException {

    public ClientException(IErrorCode errorCode) {
        this(null, null, errorCode);
    }

    public ClientException(String message) {
        this(message, null, BaseErrorCode.CLIENT_ERROR);
    }

    public ClientException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public ClientException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "ClientException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}
