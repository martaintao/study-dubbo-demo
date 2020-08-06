package com.martain.study;


import com.martain.study.vo.UserVO;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:50 下午
 */
public interface IAdminService {
    UserVO findUserById(String userId);
}
