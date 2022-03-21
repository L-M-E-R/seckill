package com.lmer.seckill.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmer.seckill.entity.Product;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.mapper.ProductMapper;
import com.lmer.seckill.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * (Product)表服务实现类
 *
 * @author Lmer
 * @since 2022-03-21 20:23:07
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Override
    public ResponseResult getList(Integer pageNum, Integer pageSize) {
        Page<Product> page = new Page<>();
        page(page);

        List<Product> products = page.getRecords();

        return ResponseResult.okResult(products);
    }

    @Override
    public ResponseResult addProduct(Product product) {
        save(product);

        return ResponseResult.okResult();
    }
}

