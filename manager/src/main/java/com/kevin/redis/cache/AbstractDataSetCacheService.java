package com.kevin.redis.cache;

import com.alibaba.fastjson.JSON;
import com.kevin.redis.service.RedisSetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
public abstract class AbstractDataSetCacheService<V> implements DataCacheService<V>{
    private static final Logger log = LoggerFactory.getLogger(AbstractDataSetCacheService.class);
    protected Class<V> objectClass = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    protected RedisSetService redisSetService;

    public AbstractDataSetCacheService() {
        super();
    }

    @Override
    public int init() {
        List<V> data = getInitData();
        if(data != null && !data.isEmpty()){
            addBatch(data);
            return data.size();
        }
        return 0;
    }

    @Override
    public int clear() {
        redisSetService.delete(getKey());
        return 0;
    }

    @Override
    public int delete(String key) {
        redisSetService.delete(key);
        return 0;
    }

    /**
     *
     * @param key 键
     * @return
     */
    @Override
    public V get(String key) {
        Set<V> set = redisSetService.getAll(key);
        List<V> results = new ArrayList<>(set.size());
        for(V v : set){
            results.add(v);
        }
        return JSON.parseObject(results.toString(),objectClass);
    }

    @Override
    public void update(V value) {
        add(value);
    }

    /**
     *
     * @param list 缓存list
     */
    @Override
    public void addBatch(List<V> list) {
        try{
            for (V v : list){
                add(v);
            }
        }catch (Exception e) {
            log.error("", e);
        }

    }

    /**
     * 取出list
     * @return
     */
    @Override
    public List<V> getAll() {
        Set<V> set = redisSetService.getAll(getKey());
        List<V> results = new ArrayList<>(set.size());
        for (V v : set){
            results.add(v);
        }
        return results;
    }

    @Override
    public void add(V v) {
        final String value = getValue(v);
        try {
            redisSetService.add(getKey(), value);
        } catch (Exception e) {
            log.error("",e);
        }

    }

    /**
     * 返回相同前缀的keys集
     * @return
     */
    public Set<V> keys(){
        return redisSetService.keys(getKey()+"*");
    }

    /**
     * 序列化对象
     * @param v 缓存对象
     * @return String
     */
    protected String getValue(V v) {
        return JSON.toJSONString(v);
    }
    /**
     * 获取key
     * @return 键
     */
    protected abstract String getKey();

    /**
     * 每次重启时要初始化到redis中的数据
     */
    protected abstract List<V> getInitData();
}
