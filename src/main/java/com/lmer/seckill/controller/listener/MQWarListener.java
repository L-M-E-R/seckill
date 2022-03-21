/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-21 22:47
 **/
package com.lmer.seckill.controller.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class MQWarListener {
    public static final String WARNING_QUEUE_NAME = "warQueue";

    @RabbitListener(queues = WARNING_QUEUE_NAME)
    public void receiveWarningMsg(Message message) {
        String msg = new String(message.getBody());
        log.error("报警发现不可路由消息：{}", msg);
    }

}
