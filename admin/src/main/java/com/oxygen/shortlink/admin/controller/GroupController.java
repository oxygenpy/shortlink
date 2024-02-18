package com.oxygen.shortlink.admin.controller;

import com.oxygen.shortlink.admin.service.GroupService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author LiJinLong
 * @description 短连接分组控制层
 * @create 2024-02-18 15:26
 * @date 1.0
 */
@RestController
@AllArgsConstructor
public class GroupController {

    private final GroupService groupService;



}
