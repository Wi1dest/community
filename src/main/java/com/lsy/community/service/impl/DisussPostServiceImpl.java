package com.lsy.community.service.impl;

import com.lsy.community.dao.DiscussPostMapper;
import com.lsy.community.entity.DiscussPost;
import com.lsy.community.service.DisussPostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname DisussPostServiceImpl
 * @Description 帖子的业务层实现类
 * @Date 2020/04/25 18:38
 */
@Service
public class DisussPostServiceImpl implements DisussPostService {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Override
    public List<DiscussPost> findDiscussPosts(int userId, int offset, int limit) {
        return discussPostMapper.selectDiscssPosts(userId,offset,limit);
    }

    @Override
    public int findDiscussPostRow(int userId) {
        return discussPostMapper.selectDiscussPostRows(userId);
    }
}
