package com.lsy.community.dao;

import com.lsy.community.entity.LoginTicket;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname LoginTicketMapperTest
 * @Description TODO
 * @Date 2020/04/27 14:33
 */
@SpringBootTest
class LoginTicketMapperTest {

    @Autowired
    private LoginTicketMapper loginTicketMapper;

    @Test
    void insertLoginTicket() {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(1);
        loginTicket.setTicket("asfasfsafaf");
        loginTicket.setStatus(1);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + 1000 * 60 * 10));
        loginTicketMapper.insertLoginTicket(loginTicket);
    }

    @Test
    void selectByTicket() {
        LoginTicket asfasfsafaf = loginTicketMapper.selectByTicket("asfasfsafaf");
        System.out.println(asfasfsafaf);
    }

    @Test
    void updateStatus() {
        int asfasfsafaf = loginTicketMapper.updateStatus("asfasfsafaf", 0);
        System.out.println(asfasfsafaf);
    }
}