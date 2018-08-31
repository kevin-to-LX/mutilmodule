package com.kevin.redis.cache.impl;

import com.kevin.redis.cache.AbstractDataSetCacheService;
import com.kevin.redis.constants.RedisConstants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/30.
 */
@Component
public class UserLoginSetCache extends AbstractDataSetCacheService<UserLoginSetCache> {

    @Override
    protected String getKey() {
        return RedisConstants.USER_LOGIN_SESSION;
    }

    @Override
    protected List<UserLoginSetCache> getInitData() {
        return null;
    }

}
