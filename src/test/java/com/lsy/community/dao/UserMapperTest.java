package com.lsy.community.dao;

import com.lsy.community.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname UserMapperTest
 * @Description TODO
 * @Date 2020/04/25 16:56
 */
@SpringBootTest
class UserMapperTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    void selectById() {
        User user = userMapper.selectById(101);
        System.out.println(user);

    }

    @Test
    void selectByName() {
        User aaa = userMapper.selectByName("AAA");
        System.out.println(aaa);
    }

    @Test
    void selectByEmail() {
        User dasdasa = userMapper.selectByEmail("dasdasa");
        System.out.println(dasdasa);
    }

    @Test
    void insertUser() {
        User user = new User();
        user.setActivationCode("sdasdasdadad");
        user.setCreateTime(new Date());
        user.setEmail("dasdasa");
        user.setHeaderUrl("dasdasdafafag");
        user.setPassword("asdafasas");
        user.setSalt("sasd");
        user.setStatus(1);
        user.setType(1);
        user.setUsername("AAA");
        userMapper.insertUser(user);
    }

    @Test
    void updateStatus() {
        userMapper.updateStatus(101,0);
    }

    @Test
    void updateHeader() {
        userMapper.updateHeader(101,"AAAAAAAAAAAAAAAAAAAAAAAA");
    }

    @Test
    void updatePassword() {
        userMapper.updatePassword(101,"null");
    }
}