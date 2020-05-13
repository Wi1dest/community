package com.lsy.community.controller;

import com.lsy.community.entity.DiscussPost;
import com.lsy.community.entity.Page;
import com.lsy.community.service.ElasticsearchService;
import com.lsy.community.service.LikeService;
import com.lsy.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.lsy.community.util.CommunityConstant.ENTITY_TYPE_POST;

/**
 * @Author : Lo Shu-ngan
 * @Classname SearchController
 * @Description TODO
 * @Date 2020/05/13 19:49
 */
@Controller
public class SearchController {
    @Autowired
    private ElasticsearchService elasticsearchService;

    @Autowired
    private UserService userService;

    @Autowired
    private LikeService likeService;

    //search?keyword=xxx
    @GetMapping("/search")
    public String search(String keyword, Page page, Model model) {
        // 搜索帖子
        org.springframework.data.domain.Page<DiscussPost> searchResult =
                elasticsearchService.searchDiscussPost(keyword, page.getCurrent() - 1, page.getLimit());

        // 聚合数据
        List<Map<String, Object>> discussPosts = new ArrayList<>();
        if (searchResult != null) {
            for (DiscussPost post : searchResult) {
                Map<String, Object> map = new HashMap<>();
                //帖子
                map.put("post", post);
                //作者
                map.put("user", userService.findUserById(post.getUserId()));
                //点赞数
                map.put("likeCount", likeService.findEntityLikeCount(ENTITY_TYPE_POST, post.getId()));

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        model.addAttribute("keyword",keyword);

        //设置分页
        page.setPath("/search?keyword" + keyword);
        page.setRows(searchResult == null ? 0 : (int) searchResult.getTotalElements());

        return "/site/search";
    }

}
