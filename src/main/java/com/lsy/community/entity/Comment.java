package com.lsy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname Comment
 * @Description 评论实体类
 * @Date 2020/04/29 14:08
 */
@Data
public class Comment {
    private Integer id;

    private Integer userId;

    private Integer entityType;

    private Integer entityId;

    private Integer targetId;

    private String content;

    private Date createTime;
}
