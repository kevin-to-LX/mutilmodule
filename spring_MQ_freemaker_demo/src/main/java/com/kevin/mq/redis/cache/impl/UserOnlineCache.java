package com.kevin.mq.redis.cache.impl;

import com.alibaba.fastjson.JSON;
import com.kevin.mq.redis.cache.AbstractDataHashCacheService;
import com.kevin.mq.redis.constants.RedisConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/30.
 */
@Component
public class UserOnlineCache extends AbstractDataHashCacheService<User> {

    private static final Logger log = LoggerFactory.getLogger(AbstractDataHashCacheService.class);
    @Override
    protected String getHashKey(User user) {
        return String.valueOf(user.getUserId());
    }

    @Override
    protected String getKey() {
        return RedisConstants.USER_ONLINE;
    }
    @Override
    protected List<User> getInitData() {
        return null;
    }

    public void add(String scheme, User user) {
        try {
            redisHashService.put(getKey() + scheme,getHashKey(user), JSON.toJSONString(user));

        }catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
