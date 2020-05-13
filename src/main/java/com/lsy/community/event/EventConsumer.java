package com.lsy.community.event;

import com.alibaba.fastjson.JSONObject;
import com.lsy.community.entity.DiscussPost;
import com.lsy.community.entity.Event;
import com.lsy.community.entity.Message;
import com.lsy.community.service.DiscussPostService;
import com.lsy.community.service.ElasticsearchService;
import com.lsy.community.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.lsy.community.util.CommunityConstant.*;

/**
 * @Author : Lo Shu-ngan
 * @Classname EventConsumer
 * @Description kafka消费者
 * @Date 2020/05/02 17:48
 */
@Component
@Slf4j
public class EventConsumer {
    @Autowired
    private MessageService messageService;

    @Autowired
    private DiscussPostService discussPostService;

    @Autowired
    private ElasticsearchService elasticsearchService;

    @KafkaListener(topics = {TOPIC_COMMENT,TOPIC_LIKE,TOPIC_FOLLOW})
    public void handleCommentMessage(ConsumerRecord record){
        if (record == null || record.value() == null) {
            log.error("消息内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }

        // 发送站内通知
        Message message = new Message();
        message.setFromId(SYSTEM_USER_ID);
        message.setToId(event.getEntityUserId());
        message.setConversationId(event.getTopic());
        message.setCreateTime(new Date());

        Map<String,Object> content = new HashMap<>();
        content.put("userId",event.getUserId());
        content.put("entityType",event.getEntityType());
        content.put("entityId",event.getEntityId());
        if (!event.getData().isEmpty()){
            for (Map.Entry<String,Object> entry : event.getData().entrySet()){
                content.put(entry.getKey(),entry.getValue());
            }
        }

        message.setContent(JSONObject.toJSONString(content));
        messageService.addMessage(message);
    }

    /**
     * 消费发帖事件
     * @param record
     */
    @KafkaListener(topics = {TOPIC_PUBLISH})
    public void handlePublishMessage(ConsumerRecord record){
        if (record == null || record.value() == null) {
            log.error("消息内容为空!");
            return;
        }

        Event event = JSONObject.parseObject(record.value().toString(),Event.class);
        if (event == null) {
            log.error("消息格式错误!");
            return;
        }

        DiscussPost post = discussPostService.findDiscussPostById(event.getEntityId());
        elasticsearchService.saveDiscussPost(post);

    }
}
