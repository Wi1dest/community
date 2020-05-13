package com.lsy.community.service;

import com.lsy.community.entity.DiscussPost;
import org.springframework.data.domain.Page;

/**
 * @Author : Lo Shu-ngan
 * @Classname ElasticsearchService
 * @Description TODO
 * @Date 2020/05/13 19:17
 */
public interface ElasticsearchService {
    void saveDiscussPost(DiscussPost post);

    void deleteDiscussPost(int id);

    Page<DiscussPost> searchDiscussPost(String keyword,int current,int limit );
}
