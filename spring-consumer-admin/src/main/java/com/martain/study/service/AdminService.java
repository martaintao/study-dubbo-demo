package com.martain.study.service;

import com.martain.study.IAdminService;
import com.martain.study.IUserService;
import com.martain.study.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:51 下午
 */
@Service
public class AdminService implements IAdminService {

    @Autowired
    IUserService userService;

    public UserVO findUserById(String userId) {
        UserVO userVO  = userService.getUserById(userId);
        System.out.println(userVO);
        return userVO;
    }
}
