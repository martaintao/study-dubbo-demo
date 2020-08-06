package com.martain.study;

import com.martain.study.vo.UserVO;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:12 下午
 */
public interface IUserService {
    UserVO getUserById(String userId);
}
