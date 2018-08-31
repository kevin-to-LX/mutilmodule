package com.kevin.redis.cache.impl;

import com.kevin.redis.cache.AbstractDataCacheService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/30.
 */
@Component
public class EzvizAlarmConsumerCache extends AbstractDataCacheService<String> {

    @Override
    protected List<String> getInitData() {
        return null;
    }

    @Override
    protected String getKeyPrefix() {
        return "ezviz_alarm_consumerid";
    }

    @Override
    protected String getKey(String s) {
        return "";
    }
}
