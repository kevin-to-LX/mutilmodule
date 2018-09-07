package com.kevin.mq.controller;

import com.kevin.mq.entity.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinyugai on 2018/8/28.
 */
@RestController
@Api(tags = "用户API")
@RequestMapping("/user")
public class UserController {

    @PostMapping("add")
    @ApiOperation(value = "新增用户")

    //正常业务时， 需要在user类里面进行事务控制，控制层一般不进行业务控制的。
    //@Transactional(rollbackFor = Exception.class)
    public Map addUser(@Valid @RequestBody User user){
        User user1 = new User();
        user1.setUserId(user.getUserId());

        Map<String, String> result = new HashMap<>();
        result.put("respCode", "01");
        result.put("respMsg", "新增成功");
        return result;
    }
}
