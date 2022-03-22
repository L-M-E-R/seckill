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
import com.lmer.seckill.utils.RedisCache;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Component
public class SkListener {

    @Autowired
    public RedisCache redisCache;

    /**
     * 秒杀队列
     */
    public static final String QUEUE= "Q";
    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "DQ";
    /**
     * 备份队列
     */
    public static final String BACKUP_QUEUE_NAME = "bkQueue";

    @RabbitListener(queues = QUEUE)
    public void receiveSkMessage(Message message){
        JSONObject jsonObject = JSON.parseObject(message.toString());

        Long userId = Long.valueOf(jsonObject.get("userId").toString());
        Long proId = Long.valueOf(jsonObject.get("proId").toString());

        if (doSk(userId, proId)) {
            // TODO 秒杀成功
        } else {
            // 秒杀失败, 往redis中存入id
            redisCache.setCacheObject("sk:fail:" + proId, userId);
        }
    }

    /**
     * TODO 死信队列处理, 就是没有秒杀成功
     */
    @RabbitListener(queues = DEAD_LETTER_QUEUE)
    public void receiveDqMessage(Message message){
        JSONObject jsonObject = JSON.parseObject(message.toString());

        Long userId = Long.valueOf(jsonObject.get("userId").toString());
        Long proId = Long.valueOf(jsonObject.get("proId").toString());

        redisCache.setCacheObject("sk:fail:" + proId, userId);
    }

    /**
     * TODO 备份队列处理, 也是秒杀失败??
     */
    @RabbitListener(queues = BACKUP_QUEUE_NAME)
    public void receiveBkMessage(Message message){
        JSONObject jsonObject = JSON.parseObject(message.toString());

        Long userId = Long.valueOf(jsonObject.get("userId").toString());
        Long proId = Long.valueOf(jsonObject.get("proId").toString());

        redisCache.setCacheObject("sk:fail:" + proId, userId);
    }


    private Boolean doSk(Long userId, Long proId){
        // 1.检查秒杀是否开始
        Object cacheObject = redisCache.getCacheObject("sk:" + proId);
        if(Objects.isNull(cacheObject)){
            return false;
        }
        Integer num = Integer.valueOf(cacheObject.toString());

        // 2.检查商品数量
        if(num < 1){
            return false;
        }

        // 3.开始进行秒杀
        redisCache.watch("sk:" + proId);
        redisCache.multi();
        //库存-1
        num--;
        redisCache.setCacheObject("sk:" + proId, num);
        //在成功列表中添加用户id
        Set<Long> sucList = redisCache.getCacheSet("sk:success:" + proId);
        sucList.add(userId);
        redisCache.setCacheSet("sk:" + proId + ":success", sucList);
        List exec = redisCache.exec();
        return exec != null && exec.size() != 0;
    }

}
