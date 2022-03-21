/**
 * @program: seckill
 * @description: 注入Bean
 * @author: Lmer
 * @create: 2022-03-21 21:52
 **/
package com.lmer.seckill.config;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public RateLimiter rateLimiter(){
        return RateLimiter.create(2000.0);
    }

}
