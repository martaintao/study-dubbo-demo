package com.martain.study.service;


import com.martain.study.IUserService;
import com.martain.study.vo.UserVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:34 下午
 */
@Service
@Component
public class UserService implements IUserService {

    @Override
    public UserVO getUserById(String userId) {
        UserVO userVO = new UserVO();
        userVO.setId(userId);
        userVO.setUserName("user-"+userId);
        userVO.setAge(18);
        userVO.setEmail(userId+"@qq.com");
        return userVO;
    }
}
