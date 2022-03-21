/**
 * @program: SecurityDemo
 * @description:  授权失败处理
 * @author: Lmer
 * @create: 2022-03-14 18:32
 **/
package com.lmer.seckill.handler;

import com.alibaba.fastjson.JSON;

import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.enums.AppHttpCodeEnum;
import com.lmer.seckill.utils.WebUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        accessDeniedException.printStackTrace();
        ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NO_OPERATOR_AUTH);
        //响应给前端
        WebUtils.renderString(response, JSON.toJSONString(result));
    }
}
