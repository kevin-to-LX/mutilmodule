package com.kevin.redis.cache;

import org.springframework.data.redis.core.ZSetOperations;

/**
 * Created by jinyugai on 2018/8/28.
 */
public abstract class AbstractDataZSetCacheService<V> implements DataCacheService<ZSetOperations> {
}
