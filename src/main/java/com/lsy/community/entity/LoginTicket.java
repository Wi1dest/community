package com.lsy.community.entity;

import lombok.Data;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname LoginTicket
 * @Description 登录凭证实体
 * @Date 2020/04/27 14:18
 */
@Data
public class LoginTicket {
    private Integer id;

    private Integer userId;

    private String ticket;

    private Integer status;

    private Date expired;
}
