package com.lsy.community.service;

/**
 * @Author : Lo Shu-ngan
 * @Classname FollowService
 * @Description 关注业务层接口类
 * @Date 2020/05/01 18:19
 */
public interface FollowService {
    void follow(int userId,int entityType,int entityId);

    void unfollow(int userId, int entityType, int entityId);

    long findFolloweeCount(int userId,int entityType);

    long findFollowerCount(int entityType,int entityId);

    boolean hasFollowed(int userId, int entityType,int entityId);
}
