package com.lmer.seckill.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmer.seckill.entity.Product;
import com.lmer.seckill.mapper.ProductMapper;
import com.lmer.seckill.service.ProductService;
import org.springframework.stereotype.Service;

/**
 * (Product)表服务实现类
 *
 * @author Lmer
 * @since 2022-03-21 20:23:07
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}

