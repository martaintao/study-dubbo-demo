package com.martain.study.service;


import com.martain.study.IUserService;
import com.martain.study.vo.UserVO;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:34 下午
 */
public class UserService implements IUserService {

    public UserVO getUserById(String userId) {
        UserVO userVO = new UserVO();
        userVO.setId(userId);
        userVO.setUserName("user-"+userId);
        userVO.setAge(18);
        userVO.setEmail(userId+"@qq.com");
        return userVO;
    }
}
