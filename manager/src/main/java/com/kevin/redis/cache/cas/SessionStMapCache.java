package com.kevin.redis.cache.cas;


import com.kevin.redis.cache.AbstractDataHashCacheService;
import com.kevin.redis.constants.RedisConstants;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * session和ST的映射
 * key: sessionId
 * value: ST
 * Created by chenzhaoming on 2018/1/8.
 */
@Component
public class SessionStMapCache extends AbstractDataHashCacheService<SessionInfo> {
    @Override
    protected String getHashKey(SessionInfo sessionInfo) {
        return sessionInfo.getSessionId();
    }

    @Override
    protected String getKey() {
        return RedisConstants.SESSION_ST;
    }

    @Override
    protected List<SessionInfo> getInitData() {
        return null;
    }
}