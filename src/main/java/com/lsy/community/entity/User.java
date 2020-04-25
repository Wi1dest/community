package com.lsy.community.entity;


import lombok.Data;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname User
 * @Description 用户实体类
 * @Date 2020/04/25 16:25
 */
@Data
public class User {
    private Integer id;

    private String username;

    private String password;

    private String salt;

    private String email;

    private Integer type;

    private Integer status;

    private String activationCode;

    private String headerUrl;

    private Date createTime;
}
