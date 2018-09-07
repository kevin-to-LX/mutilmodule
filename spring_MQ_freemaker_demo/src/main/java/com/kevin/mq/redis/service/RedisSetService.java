package com.kevin.mq.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @version
 * Created by jinyugai on 2018/8/29.
 */
@Service
public class RedisSetService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置set
     * @param value
     */
    public void add(String key, String... value){
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 删除set集合属性
     * @param key
     * @param value
     */
    public void remove(String key, String value){
        redisTemplate.opsForSet().remove(key,value);
    }
    /**
     * 获取集合
     * @param key
     * @return
     */

    public Set getAll(String key){
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 清除key
     * @param key
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    /**
     * 获取key
     * @param key
     * @return
     */
    public Set keys(String key){
        return redisTemplate.keys(key);
    }
}
