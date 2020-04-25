package com.lsy.community.service;

import com.lsy.community.entity.DiscussPost;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname DisussPostService
 * @Description 帖子的业务层接口类
 * @Date 2020/04/25 18:33
 */
public interface DisussPostService {
    List<DiscussPost> findDiscussPosts(int userId,int offset,int limit);

    int findDiscussPostRow(int userId);
}
