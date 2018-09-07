package com.kevin.mq.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Collection;
import java.util.Set;

/**
 * redis字符操作类
 * Created by jinyugai on 2018/8/29.
 */
@Service
public class RedisStringService {
    private Logger logger = LoggerFactory.getLogger(RedisStringService.class);

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static String redisCode = "UTF-8";

    /**
     * 删除缓存
     * @param keys
     */
    public void del(final String... keys){
        for (int i = 0; i < keys.length ; i++){
            redisTemplate.delete(keys[i]);
        }
    }

    /**
     * 删除缓存
     * @param keys
     */
    public void del(Collection<String> keys){
        redisTemplate.delete(keys);
    }
    /**
     * 保存缓存，有时效性
     *
     * @param key      键
     * @param value    值
     * @param liveTime 存活时间
     */
    public void set(final byte[] key, final byte[] value, final long liveTime){
        redisTemplate.execute((RedisConnection connection) -> {
            try{
                connection.set(key, value);
                if (liveTime > 0){
                    connection.expire(key, liveTime);
                }
                return 1L;
            } catch (Exception e) {
                logger.error("set:", e);
                return 0L;
            }
        });
    }

    /**
     * 保存缓存，有时效性
     *
     * @param key      键
     * @param value    值
     * @param liveTime 存活时间
     */
    public void set(String key, String value, long liveTime){
        set(key.getBytes(Charset.forName(redisCode)),value.getBytes(Charset.forName(redisCode)),liveTime);
    }

    /**
     * 保存缓存
     *
     * @param key   键
     * @param value 值
     */
    public void set(byte[] key, byte[] value){
        redisTemplate.execute((RedisConnection connection) ->{
            try{
                connection.set(key,value);
                return 1L;
            } catch (Exception e){
                logger.error("set :",e);
                return 0L;
            }
        });
    }
    /**
     * 保存缓存
     *
     * @param key      键
     * @param value    值
     */
    public void set(String key, String value){
        set(key.getBytes(Charset.forName(redisCode)),value.getBytes(Charset.forName(redisCode)));
    }

    /**
     * 获取缓存的值
     *
     * @param key 主键
     */
    public String get(String key){
        return redisTemplate.execute((RedisConnection connection) -> {
            try {
                byte[] bytes = connection.get(key.getBytes());
                return bytes != null ? new String(bytes, redisCode): null;
            } catch (UnsupportedEncodingException e){
                logger.error("get:",e);
            }
            return null;
        });
    }

    /**
     * 获取所有符合条件的主键
     *
     * @param pattern 主键正则表达式
     */
    public Set<String> keys(String pattern){
        return redisTemplate.keys(pattern);
    }

    /**
     * 清空所有缓存(谨慎使用)
     */
    public String flushDB(){
        return redisTemplate.execute((RedisConnection connection) -> {
            connection.flushDb();
            return "ok";
        });
    }
}
