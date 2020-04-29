package com.lsy.community.dao;

import com.lsy.community.entity.Comment;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname CommentMapper
 * @Description 评论Mapper接口类
 * @Date 2020/04/29 14:10
 */
public interface CommentMapper {

    List<Comment> selectCommentsByEntity(int entityType, int entityId, int offset, int limit);

    int selectCountByEntity(int entityType,int entityId);
}
