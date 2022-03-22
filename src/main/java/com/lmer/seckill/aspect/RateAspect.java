/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-22 08:37
 **/
package com.lmer.seckill.aspect;

import com.google.common.util.concurrent.RateLimiter;
import com.lmer.seckill.annotation.HttpLog;
import com.lmer.seckill.annotation.RateLimit;
import com.lmer.seckill.exception.BusinessException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

@Component
@Aspect
public class RateAspect {

    /**
     * 使用ConcurrentHashMap保证多线程可用
     */
    private final ConcurrentHashMap<String, RateLimiter> EXISTED_RATE_LIMITERS = new ConcurrentHashMap<>();

    @Pointcut("@annotation(com.lmer.seckill.annotation.RateLimit)")
    public void pt(){

    }

    @Around("pt()")
    public Object rateLimit(ProceedingJoinPoint joinPoint) throws Throwable {
        // 通过类名和方法名来进行特异性处理
        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();
        String limiterName = declaringTypeName + methodName;

        // 获取该方法设置的limit值
        RateLimit rateLimit = getAnnotation(joinPoint);
        int limit = rateLimit.limit();

        // TODO 如果是初始化加载时, 是多条请求同时请求, 会出现重复初始化rateLimiter的情况

        RateLimiter rateLimiter = EXISTED_RATE_LIMITERS.computeIfAbsent(limiterName, key -> RateLimiter.create(limit));

        // 尝试获取令牌
        if(rateLimiter.tryAcquire(1, 1, TimeUnit.SECONDS)){
            return joinPoint.proceed();
        }else{
            throw new BusinessException("当前系统繁忙, 请稍后再试");
        }
    }

    private RateLimit getAnnotation(ProceedingJoinPoint joinPoint){
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法的注解对象
        return methodSignature.getMethod().getAnnotation(RateLimit.class);
    }
}
