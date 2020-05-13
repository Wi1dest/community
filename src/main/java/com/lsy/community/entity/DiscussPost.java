package com.lsy.community.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.util.Date;

/**
 * @Author : Lo Shu-ngan
 * @Classname DiscussPost
 * @Description 帖子信息实体类
 * @Date 2020/04/25 16:34
 */
@Data
@Document(indexName = "discusspost",type = "_doc",shards = 6,replicas = 3)
public class DiscussPost {
    @Id
    private Integer id;

    //@Field(type = FieldType.Integer)
    private Integer userId;

    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String title;

    @Field(analyzer = "ik_max_word",searchAnalyzer = "ik_smart")
    private String content;

    //@Field(type = FieldType.Integer)
    private Integer type;

    //@Field(type = FieldType.Integer)
    private Integer status;

   // @Field(type = FieldType.Date)
    private Date createTime;

    //@Field(type = FieldType.Integer)
    private Integer commentCount;

    //@Field(type = FieldType.Double)
    private double score;
}
