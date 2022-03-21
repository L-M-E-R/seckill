package com.lmer.seckill.utils;


import com.lmer.seckill.entity.LoginUser;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils
{

    /**
     * 获取用户
     **/
    public static LoginUser getLoginUser()
    {
        System.out.println(getAuthentication().getPrincipal().toString());
        LoginUser loginUser = (LoginUser) getAuthentication().getPrincipal();
        return loginUser;
    }

    /**
     * 获取Authentication
     */
    public static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    public static Boolean isAdmin(){
        Integer id = getLoginUser().getUser().getId();
        return id != null && 1 == id;
    }

    public static Integer getUserId() {
        return getLoginUser().getUser().getId();
    }
}