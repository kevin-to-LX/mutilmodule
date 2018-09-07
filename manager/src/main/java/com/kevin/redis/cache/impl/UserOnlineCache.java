package com.kevin.redis.cache.impl;

import com.alibaba.fastjson.JSON;
import com.kevin.entity.Tuser;
import com.kevin.redis.cache.AbstractDataHashCacheService;
import com.kevin.redis.constants.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/30.
 */
@Component
public class UserOnlineCache extends AbstractDataHashCacheService<Tuser> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDataHashCacheService.class);
    @Override
    protected String getHashKey(Tuser user) {
        return String.valueOf(user.getId());
    }

    @Override
    protected String getKey() {
        return RedisConstants.USER_ONLINE;
    }
    @Override
    protected List<Tuser> getInitData() {
        return null;
    }

    public void add(String scheme, Tuser user) {
        try {
            redisHashService.put(getKey() + scheme,getHashKey(user), JSON.toJSONString(user));

        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
