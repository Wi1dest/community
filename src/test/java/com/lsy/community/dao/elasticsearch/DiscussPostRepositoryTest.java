package com.lsy.community.dao.elasticsearch;

import com.lsy.community.dao.DiscussPostMapper;
import com.lsy.community.entity.DiscussPost;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscussPostRepositoryTest
 * @Description TODO
 * @Date 2020/05/12 23:55
 */
@SpringBootTest
class DiscussPostRepositoryTest {
    @Autowired
    private DiscussPostMapper discussPostMapper;

    @Autowired
    private DiscussPostRepository discussPostRepository;

    //@Autowired
    //private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    void disInsert(){
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(241));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(242));
        discussPostRepository.save(discussPostMapper.selectDiscussPostById(243));

    }

    @Test
    void testInsertList(){
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(101,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(102,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(103,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(111,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(112,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(131,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(132,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(133,0,100,0));
        discussPostRepository.saveAll(discussPostMapper.selectDiscssPosts(134,0,100,0));
    }

    @Test
    void testUpdate(){
        DiscussPost discussPost = discussPostMapper.selectDiscussPostById(110);
        discussPost.setTitle("我是新修改的信息");
        discussPostRepository.save(discussPost);
    }

    @Test
    void testDel(){
        discussPostRepository.deleteById(110);
    }

    @Test
    void testSearchByRepository(){
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.multiMatchQuery("互联网寒冬","title","content"))
                .withSort(SortBuilders.fieldSort("type").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("score").order(SortOrder.DESC))
                .withSort(SortBuilders.fieldSort("createTime").order(SortOrder.DESC))
                .withPageable(PageRequest.of(0,10))
                .withHighlightFields(
                        new HighlightBuilder.Field("title").preTags("<em>").postTags("</em>"),
                        new HighlightBuilder.Field("content").preTags("<em>").postTags("</em>")
                )
                .build();

        Page<DiscussPost> search = discussPostRepository.search(searchQuery);
        System.out.println(search.getTotalElements());
        System.out.println(search.getTotalPages());
        System.out.println(search.getNumber());
        System.out.println(search.getSize());
        for (DiscussPost discussPost : search){
            System.out.println(discussPost);
        }
    }

}