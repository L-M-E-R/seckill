/**
 * @program: seckill
 * @description: 秒杀controller
 * @author: Lmer
 * @create: 2022-03-21 22:05
 **/
package com.lmer.seckill.controller;

import com.google.common.util.concurrent.RateLimiter;
import com.lmer.seckill.annotation.RateLimit;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.service.SkService;
import com.lmer.seckill.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/sk")
public class SKController {

    @Autowired
    public SkService skService;


    @GetMapping("/{proId}")
    @RateLimit(limit = 1000)
    public ResponseResult doSk(@PathVariable Long proId){
        Long userId = SecurityUtils.getUserId();
        return skService.sk(userId, proId);
    }

}
