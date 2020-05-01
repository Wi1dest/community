package com.lsy.community.service;

/**
 * @Author : Lo Shu-ngan
 * @Classname LikeService
 * @Description 点赞业务层接口类
 * @Date 2020/05/01 12:53
 */
public interface LikeService {

    void like(int userId,int entityType,int entityId);

    long findEntityLikeCount(int entityType , int entityId);

    int findEntityLikeStatus(int userId,int entityType , int entityId);
}
