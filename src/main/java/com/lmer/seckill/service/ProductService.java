package com.lmer.seckill.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lmer.seckill.entity.Product;
import com.lmer.seckill.entity.ResponseResult;


/**
 * (Product)表服务接口
 *
 * @author Lmer
 * @since 2022-03-21 20:23:03
 */
public interface ProductService extends IService<Product> {

    /** 获取商品列表
     * @param pageNum 页码
     * @param pageSize  每页大小
     * @return
     */
    ResponseResult getList(Integer pageNum, Integer pageSize);

    /**
     * @param product 商品
     * @return
     */
    ResponseResult addProduct(Product product);
}
