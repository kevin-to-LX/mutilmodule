package com.kevin.mq.redis.cache;

import com.alibaba.fastjson.JSON;
import com.kevin.mq.redis.service.RedisHashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
public abstract class AbstractDataHashCacheService<V> implements DataCacheService<V>{
    private static final Logger log = LoggerFactory.getLogger(AbstractDataHashCacheService.class);
    protected Class<V> objectClass = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @Autowired
    protected RedisHashService redisHashService;

    /**
     * 初始化
     * @return
     */
    @Override
    public int init() {
        List<V> data = getInitData();
        if (data != null && !data.isEmpty()) {
            addBatch(data);
            return data.size();
        }
        return 0;
    }

    @Override
    public int clear() {
        redisHashService.delete(getKey());
        return 0;
    }

    @Override
    public int delete(String key) {
        redisHashService.remove(getKey(),key);
        return 1;
    }

    public int delete(String scheme,String key) {
        redisHashService.remove(getKey()+scheme, key);
        return 1;
    }
    @Override
    public void update(V value) {
        add(value);
    }

    @Override
    public V get(String key) {
        String jsonStr = redisHashService.get(getKey(), key);

        return JSON.parseObject(jsonStr,objectClass);
    }

    /**
     *
     * @param list 缓存list
     */
    @Override
    public void addBatch(List<V> list) {
        try{
            for(V v : list){
                add(v);
            }
        }catch (Exception e){
            log.error("",e);
        }
    }

    @Override
    public List<V> getAll() {
        Map<String, String> map = redisHashService.getAll(getKey());
        List<V> results = new ArrayList<>(map.size());
        map.forEach((k,v) -> results.add(JSON.parseObject(v,objectClass)));
        return results;
    }

    /**
     * 添加单个缓存
     * @param v 缓存对象
     */
    @Override
    public void add(V v) {
        try{
            redisHashService.put(getKey(), getHashKey(v),getValue(v));
        } catch (Exception e){
            log.error(",e");
        }
    }

    /**
     * 序列化对象
     * @param v 缓存对象
     * @return byte[]
     */
    protected String getValue(V v) {
        return JSON.toJSONString(v);
    }

    /**
     * 获取hk
     * @param v 缓存
     * @return 缓存主键
     */
    protected abstract String getHashKey(V v);

    /**
     * 获取key
     * @return 键
     */
    protected abstract String getKey();


    /**
     * 每次重启时要初始化到redis中的数据
     */
    protected  List<V> getInitData(){
        return null;
    }
    /**
     * 获取所有key
     */
    public List<String> getAllKeys() {
        Set<String> keys = redisHashService.getAllKey(getKey());
        List<String> results = new ArrayList<>(keys.size());
        results.addAll(keys);
        return results;

    }
}
