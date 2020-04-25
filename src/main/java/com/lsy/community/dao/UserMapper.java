package com.lsy.community.dao;

import com.lsy.community.entity.User;

/**
 * @Author : Lo Shu-ngan
 * @Classname UserMapper
 * @Description 用户mapper接口
 * @Date 2020/04/25 16:38
 */
public interface UserMapper {
    User selectById(int id);

    User selectByName(String username);

    User selectByEmail(String email);

    int insertUser(User user);

    int updateStatus(int id, int status);

    int updateHeader(int id, String headerUrl);

    int updatePassword(int id, String password);
}
