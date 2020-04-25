package com.lsy.community.service;

import com.lsy.community.entity.User;

/**
 * @Author : Lo Shu-ngan
 * @Classname UserService
 * @Description 用户业务层接口类
 * @Date 2020/04/25 18:38
 */
public interface UserService {
    User findUserById(int id);
}
