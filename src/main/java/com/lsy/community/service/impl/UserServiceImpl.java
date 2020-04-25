package com.lsy.community.service.impl;

import com.lsy.community.dao.UserMapper;
import com.lsy.community.entity.User;
import com.lsy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author : Lo Shu-ngan
 * @Classname UserServiceImpl
 * @Description 用户业务层实现类
 * @Date 2020/04/25 18:41
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User findUserById(int id) {
        return userMapper.selectById(id);
    }
}
