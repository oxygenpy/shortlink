package com.oxygen.shortlink.project.toolkit;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.oxygen.shortlink.project.common.constants.ShortLinkConstant;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Date;
import java.util.Optional;

/**
 * @author LiJinLong
 * @description 短链接工具类
 * @create 2024-03-01 20:28
 * @date 1.0
 */
public class LinkUtil {

    /**
     * 根据有效期获取毫秒数
     * @param validDate 短链接有效期
     * @return 有效期毫秒数
     */
    public static long getLinkCacheValidTime(Date validDate) {
        return Optional.ofNullable(validDate)
                .map(each -> DateUtil.between(new Date(), each, DateUnit.MS))
                .orElse(ShortLinkConstant.DEFAULT_CACHE_VALID_TIME);
    }

    /**
     * 获取用户真实IP
     *
     * @param request 请求
     * @return 用户真实IP
     */
    public static String getActualIp(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-Forwarded-For");
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

}
