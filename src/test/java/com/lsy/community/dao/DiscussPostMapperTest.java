package com.lsy.community.dao;

import com.lsy.community.entity.DiscussPost;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscssPostMapperTest
 * @Description TODO
 * @Date 2020/04/25 18:15
 */
@SpringBootTest
class DiscussPostMapperTest {
    private static final Logger log = LoggerFactory.getLogger(DiscussPostMapperTest.class);

    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Test
    void selectDiscssPosts() {
        List<DiscussPost> discussPosts = discussPostMapper.selectDiscssPosts(101, 2, 15);
        for (DiscussPost discussPost : discussPosts){
            System.out.println(discussPost);
        }
        log.info("查出结果为: {}"+ discussPosts);
        log.warn("sdsadad");
        log.error(":阿瑟大大大");
    }

    @Test
    void selectDiscussPostRows() {
        int i = discussPostMapper.selectDiscussPostRows(101);
        System.out.println(i);
    }
}