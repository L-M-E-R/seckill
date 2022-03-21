/**
 * @program: seckill
 * @description: 用户controller
 * @author: Lmer
 * @create: 2022-03-21 20:51
 **/
package com.lmer.seckill.controller;

import com.lmer.seckill.annotation.HttpLog;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.entity.User;
import com.lmer.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    public UserService userService;

    @PostMapping("/login")
    @HttpLog(businessName = "用户登录")
    public ResponseResult login(@RequestBody User user){
        return userService.login(user);
    }

    @PostMapping("/register")
    @HttpLog(businessName = "用户注册")
    public ResponseResult register(@RequestBody User user){
        return userService.register(user);
    }

}
