package com.kevin.redis.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
public class FeignBasicAuthRequestInterceptor implements RequestInterceptor {
    @Autowired
    protected RedisTemplate<String,String> redisTemplate;

    @Override
    public void apply(RequestTemplate requestTemplate) {
        String feignToken = redisTemplate.opsForValue().get("feignToken");
        if (StringUtils.isBlank(feignToken)){
            feignToken = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set("feignToken",feignToken);
            redisTemplate.expire("feignToken",20, TimeUnit.MINUTES);
            redisTemplate.opsForValue().set(feignToken,feignToken);
            redisTemplate.expire(feignToken, 25, TimeUnit.MINUTES);
        }
        requestTemplate.header("feignToken",feignToken);
    }
}
