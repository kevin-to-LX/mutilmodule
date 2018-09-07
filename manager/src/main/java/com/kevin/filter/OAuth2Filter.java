package com.kevin.filter;

import com.kevin.util.JwtUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.annotation.Resource;

/**
 * Created by jinyugai on 2018/9/5.
 */
/*@Configuration*/
public class OAuth2Filter extends FormAuthenticationFilter{

    /**
     * 设置 request 的键，用来保存 认证的 userID,
     */
    private final static String USER_ID = "USER_ID";

    @Resource
    private JwtUtils jwtUtils;
}
