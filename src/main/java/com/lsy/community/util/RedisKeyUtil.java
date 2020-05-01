package com.lsy.community.util;

/**
 * @Author : Lo Shu-ngan
 * @Classname RedisKeyUtil
 * @Description RedisKey生成工具
 * @Date 2020/05/01 12:51
 */
public class RedisKeyUtil {
    private static final String SPLIT = ":";
    private static final String PREFIX_ENTITY_LIKE = "like:entity";

    // 某个实体的赞
    // like:entity:entityType:entityId -> set(userId)
    public static String getEntityLikeKey(int entityType, int entityId) {
        return PREFIX_ENTITY_LIKE + SPLIT + entityType + SPLIT + entityId;
    }

}
