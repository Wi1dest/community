package com.lsy.community.service;

import com.lsy.community.entity.Comment;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname CommentService
 * @Description 评论业务接口类
 * @Date 2020/04/29 14:17
 */
public interface CommentService {
    List<Comment>  findCommentByEntity(int entityType,int entityId,int offset,int limit);

    int findCommentCount(int entityType,int entityId);
}
