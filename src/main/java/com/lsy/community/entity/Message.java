package com.lsy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname Message
 * @Description 私信实体类
 * @Date 2020/04/29 20:03
 */
@Data
public class Message {
    private int id;

    private int fromId;

    private int toId;

    private String conversationId;

    private String content;

    private int status;

    private Date createTime;
}
