package com.lsy.community.dao;

import com.lsy.community.entity.Message;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname MessageMapper
 * @Description 私信Mapper接口类
 * @Date 2020/04/29 20:04
 */
public interface MessageMapper {
    /**
     * 查询当前用户的会话列表,针对每个会话只返回一条最新私信
     * @param userId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectConversations(int userId,int offset,int limit);

    /**
     * 查询当前用户的会话数量
     * @param userId
     * @return
     */
    int selectConversationCount(int userId);

    /**
     * 查询某个会话所包含的私信列表
     * @param conversationId
     * @param offset
     * @param limit
     * @return
     */
    List<Message> selectLetters(String conversationId,int offset,int limit);

    /**
     * 查询某个会话所包含的私信数量
     * @param conversationId
     * @return
     */
    int selectLetterCount(String conversationId);

    /**
     * 查询未读私信数量
     * @param userId
     * @return
     */
    int selectLetterUnreadCount(int userId,String conversationId);
}
