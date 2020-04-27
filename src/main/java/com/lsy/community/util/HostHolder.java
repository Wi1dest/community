package com.lsy.community.util;

import com.lsy.community.entity.User;
import org.springframework.stereotype.Component;

/**
 * @Author : Lo Shu-ngan
 * @Classname HostHolder
 * @Description 持有用户的信息,用于代替session对象
 * @Date 2020/04/27 20:14
 */
@Component
public class HostHolder {

    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user){
        users.set(user);
    }

    public User getUser(){
        return users.get();
    }

    public void clear(){
        users.remove();
    }
}
