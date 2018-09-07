package com.kevin.mq.controller;

import com.kevin.mq.redis.cache.cas.SessionStMapCache;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jinyugai on 2018/8/28.
 */
@RestController
@RequestMapping("redis")
@Api(tags = "redis 测试 API")
public class RedisController {



  /*  private UserLoginCache userLoginCache;*/
    @Autowired
    RedisTemplate redisTemplate;
    @Autowired
    SessionStMapCache sessionStMapCache;

    @GetMapping("set/{key}/{value}")
    @ApiOperation(value = "设置缓存")
    public String set(@PathVariable("key") String key, @PathVariable("value") String value){
        //注意这里的 key不能为null spring 内部有检验
        redisTemplate.opsForValue().set(key, value);

        return key + ":" + value;
    }

    @GetMapping("get/{key}")
    @ApiOperation(value="根据key获取缓存")
    public String get(@PathVariable("key") String key) {

        return "key=" + key + ",value=" + redisTemplate.opsForValue().get(key);
    }
}
