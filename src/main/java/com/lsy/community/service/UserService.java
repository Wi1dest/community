package com.lsy.community.service;

import com.lsy.community.entity.LoginTicket;
import com.lsy.community.entity.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Map;

/**
 * @Author : Lo Shu-ngan
 * @Classname UserService
 * @Description 用户业务层接口类
 * @Date 2020/04/25 18:38
 */
public interface UserService {
    User findUserById(int id);

    Map<String,Object> register(User user);

    int activation(int userId,String code);

    Map<String,Object> login(String username,String password,int expiredSeconds);

    void logout(String ticket);

    LoginTicket findLoginTicket(String ticket);

    int updateHeader(int userId,String headerUrl);

    User findUserByUsername(String username);

    Collection<? extends GrantedAuthority> getAuthorities(int userId);
}
