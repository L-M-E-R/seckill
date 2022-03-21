package com.lmer.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lmer.seckill.entity.LoginUser;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.entity.User;
import com.lmer.seckill.entity.vo.LoginVo;
import com.lmer.seckill.enums.AppHttpCodeEnum;
import com.lmer.seckill.exception.SystemException;
import com.lmer.seckill.mapper.UserMapper;
import com.lmer.seckill.service.UserService;
import com.lmer.seckill.utils.JwtUtil;
import com.lmer.seckill.utils.RedisCache;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * (User)表服务实现类
 *
 * @author Lmer
 * @since 2022-03-21 20:23:08
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    public PasswordEncoder passwordEncoder;

    @Autowired
    public AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(authenticationToken);

        // 授权失败表示密码错误
        if(Objects.isNull(authenticate)){
            throw new RuntimeException("密码错误");
        }

        //获取userid 生成token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);

        //把用户信息存入redis
        redisCache.setCacheObject("seckill:userinfo:" + userId, loginUser, 24, TimeUnit.HOURS);

        LoginVo loginVo = new LoginVo();

        loginVo.setUsername(loginUser.getUsername())
            .setToken(jwt);

        return ResponseResult.okResult(loginVo);

    }

    @Override
    public ResponseResult register(User user) {
        // 数据的非空判断
        if(!StringUtils.hasText(user.getUsername()) ||
                !StringUtils.hasText(user.getPassword())
        ){
            throw new SystemException(AppHttpCodeEnum.BAD_REQUEST);
        }
        // 对数据进行查重
        exits(user);

        // 密码加密
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 存入数据库
        save(user);

        return ResponseResult.okResult();
    }

    private void exits(User user){
        if(count(new LambdaQueryWrapper<User>().eq(User::getUsername, user.getUsername())) > 0){
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
    }
}

