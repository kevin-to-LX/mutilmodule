package com.kevin.redis.cache.impl;


import com.kevin.redis.cache.AbstractDataCacheService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/30.
 * 用于redis启动时就放入一些平台数据
 * 这是个例子，使用时spring注入该对象即可
 */
@Component
public class DataCacheServiceImpl extends AbstractDataCacheService<String> {
    @Override
    protected List<String> getInitData() {
        /*List<String> list=new ArrayList<>();
        list.add("value01");
        list.add("value02");*/
        return null;
    }

    @Override
    protected String getKeyPrefix() {
        return "key_pre";
    }

    @Override
    protected String getKey(String s) {
        return String.valueOf(s.hashCode());
    }
}
