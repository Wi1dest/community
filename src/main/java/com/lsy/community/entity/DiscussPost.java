package com.lsy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscussPost
 * @Description 帖子信息实体类
 * @Date 2020/04/25 16:34
 */
@Data
public class DiscussPost {
    private Integer id;

    private Integer userId;

    private String title;

    private String content;

    private Integer type;

    private Integer status;

    private Date createTime;

    private Integer commentCount;

    private double score;
}
