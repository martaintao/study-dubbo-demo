package com.martain.study.controller;

import com.martain.study.IUserService;
import com.martain.study.vo.UserVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/4 4:16 下午
 */
@RestController
public class AdminController {

    @Reference
    IUserService userService;

    @GetMapping("findUserById/{userId}")
    public UserVO findUserById(@PathVariable String userId){
        return userService.getUserById(userId);
    }
}
