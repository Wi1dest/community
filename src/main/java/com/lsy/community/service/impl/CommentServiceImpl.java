package com.lsy.community.service.impl;

import com.lsy.community.dao.CommentMapper;
import com.lsy.community.entity.Comment;
import com.lsy.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname CommentServiceImpl
 * @Description 评论业务实现类
 * @Date 2020/04/29 14:18
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<Comment> findCommentByEntity(int entityType, int entityId, int offset, int limit) {
        return commentMapper.selectCommentsByEntity(entityType,entityId,offset,limit);
    }

    @Override
    public int findCommentCount(int entityType, int entityId) {
        return commentMapper.selectCountByEntity(entityType,entityId);
    }
}
