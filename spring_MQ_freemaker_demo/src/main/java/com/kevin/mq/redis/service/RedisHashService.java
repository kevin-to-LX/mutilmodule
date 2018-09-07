package com.kevin.mq.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
@Service
public class RedisHashService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 设置hash对象
     * @param key 键
     * @param map hash对象
     */

    public void putAll(String key, Map<String, String> map){
        redisTemplate.opsForHash().putAll(key,map);
    }

    /**
     * 删除hash属性
     * @param key 键
     * @param hks hash键集合
     */
    public void remove(String key, String... hks){
        redisTemplate.opsForHash().delete(key, hks);
    }

    /**
     * 设置hash对象属性
     * @param key 键
     * @param hk hash键
     * @param hv hash值
     */
    public void put(String key, String hk, String hv){
        redisTemplate.opsForHash().put(key,hk,hv);
    }
    /**
     * 删除hash对象
     * @param key 键
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }
    /**
     * 获取缓存hash值
     * @param key 键
     * @param hk hash键
     */
    public String get(String key, String hk){
        return (String) redisTemplate.opsForHash().get(key,hk);
    }

    /**
     * hgetall key
     * 获取所有对象
     * @param key 键
     */
    public Map<String, String> getAll(String key){
        Map<String, String> result = new HashMap<>();
        Map<Object, Object> map = redisTemplate.opsForHash().entries(key);
        map.forEach((k, v) -> result.put((String) k,(String) v));
        return result;
    }

    /**
     * 获取所有hashkey
     * @param key 键
     */
    public Set<String> getAllKey(String key){
        Set keys = redisTemplate.opsForHash().keys(key);
        return keys;
    }
    /**
     * 查看哈希表中字段的数量
     * @param key 键
     * @return 数量
     */
    public long length(String key){
        return redisTemplate.opsForHash().size(key);
    }
}
