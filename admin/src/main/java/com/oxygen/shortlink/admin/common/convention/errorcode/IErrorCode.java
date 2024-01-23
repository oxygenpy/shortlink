package com.oxygen.shortlink.admin.common.convention.errorcode;

/**
 * @author LiJinLong
 * @description 平台错误码
 * @create 2024-01-22 20:33
 * @date 1.0
 */

public interface IErrorCode {

    /**
     * 错误码
     */
    String code();

    /**
     * 错误信息
     */
    String message();
}
