package com.lsy.community.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;

import java.util.concurrent.TimeUnit;

/**
 * @Author : Lo Shu-ngan
 * @Classname RedisConfigTest
 * @Description TODO
 * @Date 2020/04/30 21:36
 */
@SpringBootTest
class RedisConfigTest {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    void testStrings() {
        String redisKey = "test:count";
        redisTemplate.opsForValue().set(redisKey,1);
        System.out.println(redisTemplate.opsForValue().get(redisKey));
        // + 1
        System.out.println(redisTemplate.opsForValue().increment(redisKey));
        // - 1
        System.out.println(redisTemplate.opsForValue().decrement(redisKey));
    }

    @Test
    void testHashes() {
        String redisKey = "test:user";
        redisTemplate.opsForHash().put(redisKey,"id",1);
        redisTemplate.opsForHash().put(redisKey,"username","zhangsan");
        System.out.println(redisTemplate.opsForHash().get(redisKey,"id"));
        System.out.println(redisTemplate.opsForHash().get(redisKey,"username"));
    }

    @Test
    void testLists() {
        String redisKey = "test:ids";
        redisTemplate.opsForList().leftPush(redisKey,101);
        redisTemplate.opsForList().leftPush(redisKey,102);
        redisTemplate.opsForList().leftPush(redisKey,103);
        // 获取list中有多少数据
        System.out.println(redisTemplate.opsForList().size(redisKey));
        // 获取第0个的数据
        System.out.println(redisTemplate.opsForList().index(redisKey,0));
        // 获取第0到2个的数据
        System.out.println(redisTemplate.opsForList().range(redisKey,0,2));
        // 从左边弹出3次
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
        System.out.println(redisTemplate.opsForList().leftPop(redisKey));
    }

    @Test
    void testSets() {
        String redisKey = "test:teachers";
        redisTemplate.opsForSet().add(redisKey,"刘备","关羽","张飞","赵云");
        System.out.println(redisTemplate.opsForSet().size(redisKey));
        // 随机弹出一个数据
        System.out.println(redisTemplate.opsForSet().pop(redisKey));
        // 剩下还有几个数据
        System.out.println(redisTemplate.opsForSet().members(redisKey));
    }

    @Test
    void testSortedSets() {
        String redisKey = "test:students";
        redisTemplate.opsForZSet().add(redisKey,"唐僧",91);
        redisTemplate.opsForZSet().add(redisKey,"悟空",92);
        redisTemplate.opsForZSet().add(redisKey,"八戒",51);
        redisTemplate.opsForZSet().add(redisKey,"沙僧",67);
        // 统计有几个数据
        System.out.println(redisTemplate.opsForZSet().zCard(redisKey));
        // 统计八戒多少分
        System.out.println(redisTemplate.opsForZSet().score(redisKey,"八戒"));
        // 统计八戒的排名
        System.out.println(redisTemplate.opsForZSet().reverseRank(redisKey,"八戒"));
        // 由小到大 统计前三名
        System.out.println(redisTemplate.opsForZSet().range(redisKey,0,2));
        // 由大到小 统计前三名
        System.out.println(redisTemplate.opsForZSet().reverseRange(redisKey,0,2));
    }

    @Test
    void testKeys(){
        redisTemplate.delete("test:user");
        System.out.println(redisTemplate.hasKey("test:user"));

        redisTemplate.expire("test:students",10, TimeUnit.SECONDS);
    }

    // 多次访问同一个key
    @Test
    void testBoundOperations(){
        String redisKey = "test:count";
        BoundValueOperations operations = redisTemplate.boundValueOps(redisKey);
        // + 1
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        operations.increment();
        System.out.println(operations.get());
    }

    // 编程式事务
    @Test
    void testTransactional(){
        Object obj = redisTemplate.execute(new SessionCallback() {
            @Override
            public Object execute(RedisOperations redisOperations) throws DataAccessException {
                String redisKey = "test:tx";
                // 启用事务
                redisOperations.multi();

                redisOperations.opsForSet().add(redisKey,"zhangsan");
                redisOperations.opsForSet().add(redisKey,"lisi");
                redisOperations.opsForSet().add(redisKey,"wangwu");
                System.out.println(redisOperations.opsForSet().members(redisKey));

                // 提交事务
                return redisOperations.exec();
            }
        });
        System.out.println(obj);
    }
}