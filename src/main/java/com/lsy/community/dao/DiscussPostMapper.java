package com.lsy.community.dao;

import com.lsy.community.entity.DiscussPost;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscssPost
 * @Description 帖子信息Mapper接口
 * @Date 2020/04/25 17:59
 */
public interface DiscussPostMapper {
    List<DiscussPost> selectDiscssPosts(int userId, int offset, int limit);

    /**
     * @Param用于给参数取别名,如果只有一个参数,并且在<if>里使用就必须加别名
     * @param userId
     * @return
     */
    int selectDiscussPostRows(@Param("userId") int userId);

    int insertDiscussPost (DiscussPost discussPost);

    DiscussPost selectDiscussPostById(int id);
}
