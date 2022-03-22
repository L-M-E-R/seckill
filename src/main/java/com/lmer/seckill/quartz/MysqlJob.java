/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-22 07:51
 **/
package com.lmer.seckill.quartz;

import com.lmer.seckill.entity.Product;
import com.lmer.seckill.service.ProductService;
import com.lmer.seckill.utils.RedisCache;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Slf4j
public class MysqlJob extends QuartzJobBean {

    @Autowired
    public RedisCache redisCache;

    @Autowired
    public ProductService productService;

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        List<Product> list = productService.list();

        for(Product product : list){
            Long proId = product.getId();
            // 更新秒杀成功用户表
            Set<Long> sucList = redisCache.getCacheSet("sk:success:" + proId);
            productService.updateSuc(proId, sucList);

            // 更新商品数量
            Object cacheObject = redisCache.getCacheObject("sk:" + proId);
            Integer num = Integer.valueOf(cacheObject.toString());
            productService.updateProNum(proId, num);
        }

    }
}
