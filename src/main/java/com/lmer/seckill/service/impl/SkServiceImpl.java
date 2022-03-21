/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-21 22:11
 **/
package com.lmer.seckill.service.impl;

import com.alibaba.fastjson.JSON;
import com.lmer.seckill.callBack.MyCallBack;
import com.lmer.seckill.entity.ResponseResult;
import com.lmer.seckill.entity.SkMessage;
import com.lmer.seckill.service.SkService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class SkServiceImpl implements SkService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private MyCallBack myCallBack;

    /**
     * 设置rabbitTemplate的回调对象
     */
    @PostConstruct
    public void init() {
        rabbitTemplate.setConfirmCallback(myCallBack);
        // true：交换机无法将消息进行路由时，会将该消息返回给生产者
        // false：如果发现消息无法进行路由，则直接丢弃
        rabbitTemplate.setMandatory(true);
        //设置回退消息交给谁处理
        rabbitTemplate.setReturnsCallback(myCallBack);
    }

    @Override
    public ResponseResult sk(Long userId, Long proId) {
        SkMessage message = new SkMessage();
        message.setUserId(userId)
                        .setProId(proId);

        rabbitTemplate.convertAndSend("SK", "A", JSON.toJSON(message));
        return null;
    }
}
