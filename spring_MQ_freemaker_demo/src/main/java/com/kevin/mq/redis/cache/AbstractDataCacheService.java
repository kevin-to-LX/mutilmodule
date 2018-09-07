package com.kevin.mq.redis.cache;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.lang.Nullable;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 缓存管理抽象类，保存字符串类型
 * @version 1.0.1
 * Created by jinyugai on 2018/8/28.
 */
public abstract class AbstractDataCacheService<V> implements DataCacheService<V> {
    private static final Logger log = LoggerFactory.getLogger(AbstractDataCacheService.class);

    protected Class<V> objectClass = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];
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

    /**
     * 清空 flushAll
     * @return
     */
    @Override
    public int clear() {
        try {
            if (redisTemplate == null){
                return -1;
            }
            Set<String> keys = redisTemplate.keys(getKeyPrefix() + "*");
            if (!keys.isEmpty()){
                redisTemplate.delete(keys);
                return keys.size();
            }
        }catch (Exception e){
            log.error("",e);
        }
        return 0;
    }

    @Override
    public int delete(String key) {
        try {
            if (redisTemplate == null){
                return -1;
            }
            redisTemplate.delete(getKeyPrefix() + key);
            return 1;
        } catch (Exception e){
            log.error("",e);
            return -1;
        }
    }

    @Override
    public void update(V value) {
        add(value);
    }

    /**
     * 添加
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

    /**
     * 非空缓存
     * @param list 缓存list
     */
    public void addNXBatch(final List<V> list) {
        try {
            redisTemplate.executePipelined((RedisConnection connection) -> {
                for (V v : list) {
                    byte[] key = (getKeyPrefix() + getKey(v)).getBytes();
                    connection.setNX(key, getValue(v));
                }
                return null;
            });
        } catch (Exception e) {
            log.error("", e);
        }
    }
    @Override
    public List<V> getAll() {
        try {
            final Set<String> keys = redisTemplate.keys(getKeyPrefix() + "*");
            if (keys == null || keys.size() <= 0){
                return null;
            }
            List<V> list = redisTemplate.execute(new RedisCallback<List<V>>() {
                @Nullable
                @Override
                public List<V> doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    List<V> keysResults = new ArrayList<V>(keys.size());
                    for (String key : keys){
                        byte[] bytes = redisConnection.get(key.getBytes(Charset.forName("UTF-8")));
                        if (bytes != null){
                            try {
                                String jsonStr = new String(bytes,StandardCharsets.UTF_8);
                                keysResults.add(JSON.parseObject(jsonStr, objectClass));
                            }catch (Exception e){
                                log.error("",e);
                            }

                        }
                    }
                    return keysResults;
                }
            });
            return list;
        }catch (Exception e){
            log.error("",e);
            return null;
        }
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

    @Override
    public V get(String key) {
        final byte[] keyBytes = (getKeyPrefix() + key).getBytes(Charset.forName("UTF-8"));
        try{
            return redisTemplate.execute(new RedisCallback<V>() {
                @Nullable
                @Override
                public V doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    byte[] bytes = redisConnection.get(keyBytes);
                    if (bytes != null){
                        try {
                            String jsonStr = new String(bytes,StandardCharsets.UTF_8);
                            return JSON.parseObject(jsonStr,objectClass);
                        }catch (Exception e){
                            log.error("",e);
                            return null;
                        }
                    } else {
                        return null;
                    }
                }
            });
        }catch (Exception e){
            log.error("",e);
            return null;
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
                return null;
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

    /**
     * 获取存活时间
     * @param key
     * @return
     */
    public Long getExpiredTime(String key) {

        return redisTemplate.getExpire(getKeyPrefix() + key);
    }

}
