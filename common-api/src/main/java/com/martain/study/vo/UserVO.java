package com.martain.study.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Martin
 * @version 1.0
 * @date 2020/8/3 2:22 下午
 */
@Data
public class UserVO implements Serializable {

    private String id;

    private String userName;

    private Integer age;

    private String email;

}
