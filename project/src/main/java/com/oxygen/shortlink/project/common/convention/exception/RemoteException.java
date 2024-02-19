package com.oxygen.shortlink.project.common.convention.exception;


import com.oxygen.shortlink.project.common.convention.errorcode.BaseErrorCode;
import com.oxygen.shortlink.project.common.convention.errorcode.IErrorCode;

/**
 * @author LiJinLong
 * @description 远程服务调用异常
 * @create 2024-01-22 20:41
 * @date 1.0
 */
public class RemoteException extends AbstractException {

    public RemoteException(String message) {
        this(message, null, BaseErrorCode.REMOTE_ERROR);
    }

    public RemoteException(String message, IErrorCode errorCode) {
        this(message, null, errorCode);
    }

    public RemoteException(String message, Throwable throwable, IErrorCode errorCode) {
        super(message, throwable, errorCode);
    }

    @Override
    public String toString() {
        return "RemoteException{" +
                "code='" + errorCode + "'," +
                "message='" + errorMessage + "'" +
                '}';
    }
}