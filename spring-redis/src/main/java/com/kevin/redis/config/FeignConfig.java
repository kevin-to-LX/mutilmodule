package com.kevin.redis.config;

import org.springframework.context.annotation.Configuration;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
@Configuration
public class FeignConfig {

    /**
     * 创建Feign请求拦截器，在发送请求前设置认证的token,各个微服务将token设置到环境变量中来达到通用
     * @return
     */
    /*@Bean
    public FeignBasicAuthRequestInterceptor basicAuthRequestInterceptor() {
        return new FeignBasicAuthRequestInterceptor();
    }
    @Bean
    public FeignRequestFilter feignRequestFilter(){
        return new FeignRequestFilter();
    }*/
}
