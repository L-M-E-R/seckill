/**
 * @program: seckill
 * @description: 秒杀服务
 * @author: Lmer
 * @create: 2022-03-21 22:11
 **/
package com.lmer.seckill.service;

import com.lmer.seckill.entity.ResponseResult;

public interface SkService {

    /**
     * @param userId 用户id
     * @param proId 商品id
     * @return
     */
    ResponseResult sk(Long userId, Long proId);
}
