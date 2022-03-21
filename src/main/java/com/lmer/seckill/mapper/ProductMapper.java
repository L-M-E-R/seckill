package com.lmer.seckill.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lmer.seckill.entity.Product;
import org.apache.ibatis.annotations.Mapper;


/**
 * (Product)表数据库访问层
 *
 * @author Lmer
 * @since 2022-03-21 20:22:55
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

}

