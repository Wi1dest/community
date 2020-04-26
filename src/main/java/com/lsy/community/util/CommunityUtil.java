package com.lsy.community.util;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.DigestUtils;

import java.util.UUID;

/**
 * @Author : Lo Shu-ngan
 * @Classname CommunityUtil
 * @Description 社区工具类
 * @Date 2020/04/26 16:02
 */
public class CommunityUtil {
    /**
     * 生产随机字符串
     * @return
     */
    public static String generateUUID(){
        // 生产随机字符串 并去掉 -
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * MD5加密
     * @param key
     */
    public static String md5(String key){
        if (StringUtils.isBlank(key)){
            return null;
        }
        return DigestUtils.md5DigestAsHex(key.getBytes());
    }


}
