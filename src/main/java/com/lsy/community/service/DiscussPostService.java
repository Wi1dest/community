package com.lsy.community.service;

import com.lsy.community.entity.DiscussPost;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname DisussPostService
 * @Description 帖子的业务层接口类
 * @Date 2020/04/25 18:33
 */
public interface DiscussPostService {
    List<DiscussPost> findDiscussPosts(int userId,int offset,int limit,int orderMode);

    int findDiscussPostRow(int userId);

    int addDiscussPost(DiscussPost post);

    DiscussPost findDiscussPostById(int id);

    int updateCommentCount(int id,int commentCount);

    int updateType(int id,int tpye);

    int updateStatus(int id,int status);

    int updateScore(int id,double score);
}
