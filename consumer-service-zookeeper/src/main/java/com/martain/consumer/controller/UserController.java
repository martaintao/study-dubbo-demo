package com.martain.consumer.controller;

import com.martain.common.api.IUserService;
import com.martain.common.api.vo.User;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @DubboReference
    IUserService userService;

    @GetMapping("/getUserInfo/{userId}")
    public Object getUserInfo(@PathVariable String userId){
        return userService.getUserById(userId);
    }
}
