package com.lsy.community.dao;

import com.lsy.community.entity.Message;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : Lo Shu-ngan
 * @Classname MessageMapperTest
 * @Description TODO
 * @Date 2020/04/29 20:24
 */
@SpringBootTest
class MessageMapperTest {
    @Autowired
    private MessageMapper messageMapper;

    @Test
    void selectConversations() {
        List<Message> messages = messageMapper.selectConversations(111, 0, 20);
        for (Message message :messages){
            System.out.println(message);
        }
    }

    @Test
    void selectConversationCount() {
        int count = messageMapper.selectConversationCount(111);
        System.out.println(count);
    }

    @Test
    void selectLetters() {
        List<Message> messages = messageMapper.selectLetters("111_112", 0, 10);
        for (Message message : messages){
            System.out.println(message);
        }
    }

    @Test
    void selectLetterCount() {
        int count = messageMapper.selectLetterCount("111_112");
        System.out.println(count);
    }

    @Test
    void selectLetterUnreadCount() {
        int count = messageMapper.selectLetterUnreadCount(111, "111_112");
        System.out.println(count);
    }
}