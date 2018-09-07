package com.kevin.redis.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
public class FeignBasicAuthRequestInterceptor /*implements RequestIntercepto */{
    @Autowired
    protected RedisTemplate<String,String> redisTemplate;

    /*@Override
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
    }*/
}
