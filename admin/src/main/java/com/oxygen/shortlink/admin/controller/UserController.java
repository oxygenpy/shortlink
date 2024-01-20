package com.oxygen.shortlink.admin.controller;

import com.oxygen.shortlink.admin.dto.resp.UserRespDTO;
import com.oxygen.shortlink.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJinLong
 * @description 用户管理控制层
 * @create 2024-01-20 19:55
 * @date 1.0
 */
@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 根据用户名查询用户
     */
    @GetMapping("/api/shortlink/v1/user/{username}")
    public UserRespDTO getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username);
    }

}
