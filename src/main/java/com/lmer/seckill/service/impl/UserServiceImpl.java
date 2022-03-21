package com.lmer.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmer.seckill.entity.User;
import com.lmer.seckill.mapper.UserMapper;
import com.lmer.seckill.service.UserService;
import org.springframework.stereotype.Service;

/**
 * (User)表服务实现类
 *
 * @author Lmer
 * @since 2022-03-21 20:23:08
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}

