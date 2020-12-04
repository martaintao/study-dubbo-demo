package com.martain.common.api;

import com.martain.common.api.vo.User;

/**
 * 用户相关接口
 */
public interface IUserService {

    /**
     * 通过Id获取用户
     * @param userId 用户ID
     * @return 用户实体
     */
    User getUserById(String userId);
}
