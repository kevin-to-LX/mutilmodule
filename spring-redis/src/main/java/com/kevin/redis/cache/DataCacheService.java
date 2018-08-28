package com.kevin.redis.cache;

import java.util.List;

/**
 * redis缓存管理类
 * Created by jinyugai on 2018/8/28.
 */
public interface DataCacheService<V> {

    /**
     * 初始化缓存数据
     * @return 缓存数量
     */
    int init();

    /**
     * 清空所有缓存数据
     * @return 清空的缓存数量
     */
    int clear();

    /**
     * 删除数据
     * @param key 键
     * @return 1表示成功
     */
    int delete(String key);

    /**
     * 更新数据
     * @param value 值
     */
    void update(V value);

    /**
     * 批量插入缓存到reids
     * @param list 缓存list
     */
    void addBatch(List<V> list);

    /**
     * 获取所有缓存
     * @return 缓存队列
     */
    List<V> getAll();

    /**
     * 添加缓存
     */
    void add(V v);
}
