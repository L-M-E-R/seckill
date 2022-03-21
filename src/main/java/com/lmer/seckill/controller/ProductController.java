/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-21 21:34
 **/
package com.lmer.seckill.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.lmer.seckill.entity.Product;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    public ProductService productService;

    @GetMapping("/getList")
    public ResponseResult getList(Integer pageNum, Integer pageSize){
        return productService.getList(pageNum, pageSize);
    }

    @GetMapping("/addProduct")
    public ResponseResult addProduct(Product product){
        return productService.addProduct(product);
    }

}
