package com.lmer.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmer.seckill.entity.SuccessUser;
import com.lmer.seckill.mapper.SuccessUserMapper;
import com.lmer.seckill.service.SuccessUserService;
import org.springframework.stereotype.Service;

/**
 * (SuccessUser)表服务实现类
 *
 * @author Lmer
 * @since 2022-03-21 20:23:08
 */
@Service("successUserService")
public class SuccessUserServiceImpl extends ServiceImpl<SuccessUserMapper, SuccessUser> implements SuccessUserService {

}

