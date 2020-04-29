package com.lsy.community.service;

import com.lsy.community.entity.Message;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname MessageService
 * @Description 私信业务层接口
 * @Date 2020/04/29 20:31
 */
public interface MessageService {
    List<Message> findConversations(int userId,int offset,int limit);

    int findConversationCount(int userId);

    List<Message> findLetters(String conversationId,int offset,int limit);

    int findLetterCount(String conversationId);

    int findLetterUnreadCount(int userId,String conversationId);
}
