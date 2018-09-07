package com.kevin.mq.redis.lock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * Created by jinyugai on 2018/8/31.
 */
@Component
public class RedisLockFactory {
    private static RedisTemplate<String, String> redisTemplate;

    public static RedisLock creatRedisLock(String lockKey) {
        return new RedisLock(redisTemplate,lockKey);
    }

    @Autowired
    public void  setRedisTemplate(RedisTemplate<String, String> redisTemplate) {
        RedisLockFactory.redisTemplate = redisTemplate;
    }
}
