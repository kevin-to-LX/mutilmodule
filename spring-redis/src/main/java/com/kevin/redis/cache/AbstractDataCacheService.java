package com.kevin.redis.cache;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by jinyugai on 2018/8/28.
 */
public abstract class AbstractDataCacheService<V> implements DataCacheService<V> {
    private static final Logger log = LoggerFactory.getLogger(AbstractDataCacheService.class);

    @Autowired
    protected RedisTemplate<String, V> redisTemplate;

    public AbstractDataCacheService() {
        super();
    }

    /**
     * 初始化
     * @return
     */
    @Override
    public int init() {
        List<V> data = getInitData();
        if (data != null && !data.isEmpty()){
            addBatch(data);
            return data.size();
        }
        return 0;
    }

    @Override
    public int clear() {
        return 0;
    }

    @Override
    public int delete(String key) {
        return 0;
    }

    @Override
    public void update(V value) {

    }

    /**
     *
     * @param list 缓存list
     */
    @Override
    public void addBatch(List<V> list) {
        try{
            redisTemplate.executePipelined((RedisConnection connection) ->{
                for (V v : list){
                    byte[] key = (getKeyPrefix() + getKey(v)).getBytes();
                    connection.set(key,getValue(v));
                }
                return null;
            });
        }catch (Exception e){
            log.error("",e);
        }
    }

    @Override
    public List<V> getAll() {
        return null;
    }

    /**
     * 添加单个缓存
     * @param v 缓存对象
     */
    @Override
    public void add(V v) {

        String keyStr = getKeyPrefix() + getKey(v);
        final byte[] key = keyStr.getBytes(Charset.forName("UTF-8"));
        final byte[] value = getValue(v);
        try{
            redisTemplate.execute((RedisConnection connection) -> {
                connection.set(key,value);
                return 1L;
            });
        }catch (Exception e){
            log.error("",e);
        }

    }

    /**
     * addnx 非空添加
     * @param v
     */
    public void addNX(V v){
        String keyStr = getKeyPrefix() + getKey(v);
        final byte[] key = keyStr.getBytes(Charset.forName("UTF-8"));
        final byte[] value = getValue(v);
        try {
            redisTemplate.execute((RedisConnection connection) -> {
                connection.setNX(key,value);
                return 1L;
            });
        }catch (Exception e){
            log.error("",e);
        }
    }


    /**
     * add key / expire key livetime
     * @param v
     * @param liveTime
     */
    public void add(V v,long liveTime){
        String keyStr = getKeyPrefix() + getKey(v);
        final byte[] key = keyStr.getBytes(Charset.forName("UTF-8"));
        final byte[] value = getValue(v);
        try {
            redisTemplate.execute((RedisConnection connection)->{
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            });
        } catch (Exception e) {
            log.error("", e);
        }
    }
    /**
     * 每次重启时要初始化到redis中的数据
     * @return
     */
    protected abstract List<V> getInitData();

    /**
     * 获取key前缀
     * @return 键前缀
     */
    protected abstract String getKeyPrefix();

    /**
     * 获取主键
     * @param v 缓存
     * @return 缓存主键
     */
    protected abstract String getKey(V v);

    /**
     * 序列化对象
     * @param v 缓存对象
     * @return byte[]
     */
    protected byte[] getValue(V v) {
        return JSON.toJSONString(v).getBytes(StandardCharsets.UTF_8);
    }
}
