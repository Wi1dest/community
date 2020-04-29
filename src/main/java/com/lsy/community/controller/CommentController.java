package com.lsy.community.controller;

import com.lsy.community.entity.Comment;
import com.lsy.community.service.CommentService;
import com.lsy.community.util.HostHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname CommentController
 * @Description 评论模块控制器
 * @Date 2020/04/29 16:39
 */
@Controller
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private HostHolder hostHolder;

    @PostMapping("/add/{discussPostId}")
    public String addComment(@PathVariable("discussPostId")int discussPostId, Comment comment){
        comment.setUserId(hostHolder.getUser().getId());
        comment.setStatus(0);
        comment.setCreateTime(new Date());
        if (comment.getTargetId() == null){
            comment.setTargetId(0);
        }
        commentService.addComment(comment);

        return "redirect:/discuss/detail/" + discussPostId;
    }

}
