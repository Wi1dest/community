package com.lsy.community.service.impl;

import com.lsy.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscussPostServiceImplTest
 * @Description TODO
 * @Date 2020/05/17 13:12
 */
@SpringBootTest
class DiscussPostServiceImplTest {
    @Autowired
    private DiscussPostServiceImpl discussPostService;

    @Test
    void addDiscussPost() {
        DiscussPost post = new DiscussPost();
        for (int i = 0;i<=30000;i++){
            post.setUserId(101);
            post.setTitle("AAAAA");
            post.setType(0);
            post.setStatus(0);
            post.setContent("SDADAAD");
            discussPostService.addDiscussPost(post);
        }
    }
}