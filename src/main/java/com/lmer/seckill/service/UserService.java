package com.lmer.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.entity.User;


/**
 * (User)表服务接口
 *
 * @author Lmer
 * @since 2022-03-21 20:23:08
 */
public interface UserService extends IService<User> {

    ResponseResult login(User user);

    ResponseResult register(User user);

}
