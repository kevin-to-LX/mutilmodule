package com.kevin.mq.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by jinyugai on 2018/8/24.
 */
@Controller
@RequestMapping("/indexPage")
@Api(tags = "HelloApi")
public class HelloFtl {
    @RequestMapping("/hello")
    public String helloFtl(Map<String, Object> map){

        map.put("hello","Hello Zncu FreeMaker");
        return "index";
    }
}
