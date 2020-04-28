package com.lsy.community.controller;

import com.lsy.community.entity.DiscussPost;
import com.lsy.community.entity.User;
import com.lsy.community.service.DiscussPostService;
import com.lsy.community.service.UserService;
import com.lsy.community.util.CommunityUtil;
import com.lsy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscussPostController
 * @Description TODO
 * @Date 2020/04/28 20:23
 */
@Controller
@RequestMapping("/discuss")
public class DiscussPostController {
    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private UserService userService;

    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/add")
    @ResponseBody
    public String addDiscussPost(String title,String content){
        User user = hostHolder.getUser();
        if (user == null) {
            return CommunityUtil.getJSONString(403,"你还没登录!");
        }
        DiscussPost post = new DiscussPost();
        post.setUserId(user.getId());
        post.setTitle(title);
        post.setContent(content);
        post.setCreateTime(new Date());
        post.setStatus(0);
        post.setType(0);
        post.setCommentCount(0);
        discussPostService.addDiscussPost(post);

        return CommunityUtil.getJSONString(0,"发布成功!");
    }

    @GetMapping("/detail/{discussPostId}")
    public String getDiscussPost(@PathVariable("discussPostId")int discussPostId, Model model){
        //帖子
        DiscussPost discussPost = discussPostService.findDiscussPostById(discussPostId);
        //传给模板
        model.addAttribute("post",discussPost);
        //查出帖子的作者
        User user = userService.findUserById(discussPost.getUserId());
        model.addAttribute("user",user);

        return "/site/discuss-detail";
    }
}
