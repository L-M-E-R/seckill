package com.lmer.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmer.seckill.entity.Product;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.entity.SuccessUser;
import com.lmer.seckill.mapper.ProductMapper;
import com.lmer.seckill.service.ProductService;
import com.lmer.seckill.service.SuccessUserService;
import com.lmer.seckill.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * (Product)表服务实现类
 *
 * @author Lmer
 * @since 2022-03-21 20:23:07
 */
@Service("productService")
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

    @Autowired
    public SuccessUserService successUserService;

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

    @Override
    public void updateSuc(Long proId, Set<Long> sucList) {
        for(Long userId : sucList){
            LambdaQueryWrapper<SuccessUser> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(SuccessUser::getUserId, userId)
                    .eq(SuccessUser::getProId, proId);

            // 查看用户是否已经存在
            long count = successUserService.count(queryWrapper);
            if(count > 0){
                continue;
            }

            SuccessUser successUser = new SuccessUser();
            successUser.setUserId(userId);
            successUser.setProId(proId);

            successUserService.save(successUser);
        }

    }

    @Override
    public void updateProNum(Long proId, Integer num) {
        Product product = getById(proId);
        product.setNumber(num);

        updateById(product);
    }


}

