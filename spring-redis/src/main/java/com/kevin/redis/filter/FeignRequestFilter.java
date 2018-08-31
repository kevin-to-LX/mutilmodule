package com.kevin.redis.filter;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version Created by jinyugai on 2018/8/29.
 */
public class FeignRequestFilter implements Filter {
    Logger logger= LoggerFactory.getLogger(FeignRequestFilter.class);
    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("feignRequestFilter init");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest){
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse =(HttpServletResponse) response;
            String path = httpServletRequest.getPathInfo();
            if(path.startsWith("/feign")){
                boolean flag = false;
                String feignToken = httpServletRequest.getHeader("feignToken");
                if (StringUtils.isNotBlank(feignToken)){
                    flag = true;
                } else {
                    String value = redisTemplate.opsForValue().get(feignToken);
                    if(StringUtils.isNotBlank(value)){
                        flag = true;
                    }
                }
                if (flag){
                    httpServletResponse.setContentType("text/html;charset=UTF-8");
                    httpServletResponse.setCharacterEncoding("UTF-8");
                    httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
                    httpServletResponse.getWriter().println("无权限访问");
                }
                chain.doFilter(request,response);
            }
        }
    }

    @Override
    public void destroy() {
        logger.info("feignRequestFilter destroy");

    }
}
