package com.lmer.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmer.seckill.entity.User;
import org.apache.ibatis.annotations.Mapper;


/**
 * (User)表数据库访问层
 *
 * @author Lmer
 * @since 2022-03-21 20:23:08
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}

