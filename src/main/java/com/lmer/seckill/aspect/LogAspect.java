/**
 * @program: LBolg
 * @description:
 * @author: Lmer
 * @create: 2022-03-21 17:45
 **/
package com.lmer.seckill.aspect;


import com.alibaba.fastjson.JSON;
import com.lmer.seckill.annotation.HttpLog;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
@Aspect
@Slf4j
public class LogAspect {

    @Pointcut("@annotation(com.lmer.seckill.annotation.HttpLog)")
    public void pt(){

    }


    @Around("pt()")
    public Object httpLogPrint(ProceedingJoinPoint joinPoint) throws Throwable {
        Object ret;

        try {
            beforeHandle(joinPoint);
            ret = joinPoint.proceed();
            afterHandle(ret);
        } finally {
            // 结束后换行
            log.info("=======End=======" + System.lineSeparator());
        }

        return ret;
    }

    private void beforeHandle(ProceedingJoinPoint joinPoint){
        // 获取请求对象
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        HttpServletRequest request = requestAttributes.getRequest();

        // 获取注解对象
        HttpLog httpLog = getHttpLog(joinPoint);

        log.info("=======Start=======");
        // 打印请求 URL
        log.info("URL            : {}", request.getRequestURI());
        // 打印描述信息
        log.info("BusinessName   : {}", httpLog.businessName());
        // 打印 Http method
        log.info("HTTP Method    : {}", request.getMethod());
        // 打印调用 controller 的全路径以及执行方法
        log.info("Class Method   : {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
        // 打印请求的 IP
        log.info("IP             : {}", request.getRemoteHost());
        // 打印请求入参
        log.info("Request Args   : {}", JSON.toJSONString(joinPoint.getArgs()));
    }

    private HttpLog getHttpLog(ProceedingJoinPoint joinPoint) {
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        // 获取方法的注解对象
        return methodSignature.getMethod().getAnnotation(HttpLog.class);
    }

    private void afterHandle(Object ret){
        // 打印出参
        log.info("Response       : {}", JSON.toJSONString(ret));
    }

}
