package com.lsy.community.service.impl;

import com.lsy.community.service.LikeService;
import com.lsy.community.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.stereotype.Service;

/**
 * @Author : Lo Shu-ngan
 * @Classname LikeServiceImpl
 * @Description 点赞业务层接口实现类
 * @Date 2020/05/01 12:55
 */
@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 点赞
     * @param userId
     * @param entityType
     * @param entityId
     */
    @Override
    public void like(int userId, int entityType, int entityId,int entityUserId) {
        //编程式事务
        redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
                String userLikeKey = RedisKeyUtil.getUserLikeKey(entityUserId);
                // 检查集合中是否包含某个元素 => 检查这个实体的赞集合中是否包含这个userId点的赞
                boolean isMember = redisOperations.opsForSet().isMember(entityLikeKey,userId);
                // .multi() 启动事务
                redisOperations.multi();
                if (isMember) {
                    // 取消赞
                    redisOperations.opsForSet().remove(entityLikeKey,userId);
                    // 该用户的赞集合数量 -1
                    redisOperations.opsForValue().decrement(userLikeKey);
                }else {
                    // 该实体赞 +1
                    redisOperations.opsForSet().add(entityLikeKey,userId);
                    // 该用户总赞数 +!
                    redisOperations.opsForValue().increment(userLikeKey);
                }
                // .exec()提交事务
                return redisOperations.exec();
            }
        });
    }

    /**
     * 查询某实体点赞数量
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public long findEntityLikeCount(int entityType , int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().size(entityLikeKey);
    }

    /**
     * 查询某人对某实体的点赞状态
     * @param userId
     * @param entityType
     * @param entityId
     * @return
     */
    @Override
    public int findEntityLikeStatus(int userId,int entityType , int entityId){
        String entityLikeKey = RedisKeyUtil.getEntityLikeKey(entityType,entityId);
        return redisTemplate.opsForSet().isMember(entityLikeKey,userId) ? 1 : 0;
    }

    /**
     * 查询某个用户获得的赞
     * @param userId
     * @return
     */
    @Override
    public int findUserLikeCount(int userId){
        String userLikeKey = RedisKeyUtil.getUserLikeKey(userId);
        Integer count = (Integer) redisTemplate.opsForValue().get(userLikeKey);
        return count == null ? 0 : count.intValue();
    }
}
