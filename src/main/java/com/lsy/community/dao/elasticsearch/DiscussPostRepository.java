package com.lsy.community.dao.elasticsearch;

import com.lsy.community.entity.DiscussPost;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscussPostRepository
 * @Description TODO
 * @Date 2020/05/12 23:53
 */
@Repository
public interface DiscussPostRepository extends ElasticsearchRepository<DiscussPost, Integer> {
}
