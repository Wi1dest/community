package com.lsy.community.controller;

import com.lsy.community.entity.DiscussPost;
import com.lsy.community.entity.Page;
import com.lsy.community.entity.User;
import com.lsy.community.service.DiscussPostService;
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
 * @Classname HomeController
 * @Description HOME页控制器
 * @Date 2020/04/25 18:46
 */
@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/index")
    public String getIndexPage(Model model, Page page){
        // 方法调用之前,SpringMVC会自动实例化model和page,而且还会将page注入model
        // 所以在thymeleat中可以直接访问page对象重点呢数据
        page.setRows(discussPostService.findDiscussPostRow(0));
        page.setPath("/index");

        List<DiscussPost> list = discussPostService.findDiscussPosts(0, page.getOffset(), page.getLimit());
        List<Map<String,Object>> discussPosts = new ArrayList<>();
        if (list != null) {
            for (DiscussPost disussPost : list){
                Map<String,Object> map = new HashMap<>();
                map.put("post",disussPost);
                User user = userService.findUserById(disussPost.getUserId());
                map.put("user",user);

                //查询赞数量
                long likeCount = likeService.findEntityLikeCount(ENTITY_TYPE_POST, disussPost.getId());
                map.put("likeCount",likeCount);

                discussPosts.add(map);
            }
        }
        model.addAttribute("discussPosts",discussPosts);
        return "/index";
    }

    /**
     * 拒绝访问时的提示页面
     * @return
     */
    @GetMapping("/denied")
    public String getDeniedPage() {
        return "/error/404";
    }
}
