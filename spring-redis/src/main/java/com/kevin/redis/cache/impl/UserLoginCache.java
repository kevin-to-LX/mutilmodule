package com.kevin.redis.cache.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;


import java.util.concurrent.TimeUnit;

/**
 * Created by jinyugai on 2018/8/30.
 */
@Component
public class UserLoginCache {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void add(String string, long liveTime) {
        redisTemplate.opsForValue().set(string,string);
        redisTemplate.expire(string, liveTime, TimeUnit.SECONDS);
    }

    public String get(String str) {
        String message = "";
        if (redisTemplate.opsForValue().get(str) != null) {
            message = redisTemplate.opsForValue().get(str);
        }
        if (message.split(",").length > 4){
            return message.split(",")[2];
        }
        return message;
    }

    public void delete(String string){
        redisTemplate.delete(string);
    }
}
