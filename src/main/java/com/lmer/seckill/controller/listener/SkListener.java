/**
 * @program: seckill
 * @description:
 * @author: Lmer
 * @create: 2022-03-21 23:02
 **/
package com.lmer.seckill.controller.listener;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class SkListener {
    /**
     * 秒杀队列
     */
    public static final String QUEUE= "Q";
    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "DQ";

    @RabbitListener(queues = QUEUE)
    public void receiveSkMessage(Message message){
        JSONObject jsonObject = JSON.parseObject(message.toString());

        Long userId = Long.valueOf(jsonObject.get("userId").toString());
        Long proId = Long.valueOf(jsonObject.get("proId").toString());


    }

}
