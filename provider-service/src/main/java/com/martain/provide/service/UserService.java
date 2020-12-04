package com.martain.provide.service;

import com.martain.common.api.IUserService;
import com.martain.common.api.vo.User;

public class UserService implements IUserService {

    public User getUserById(String userId) {
        User user = new User();
        user.setId(userId);
        user.setUserName("小明");
        user.setAge(18);
        return user;
    }
}
