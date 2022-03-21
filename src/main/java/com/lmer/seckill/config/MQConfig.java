/**
 * @program: deadQueue
 * @description:
 * @author: Lmer
 * @create: 2022-03-19 11:13
 **/
package com.lmer.seckill.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration()
public class MQConfig {
    /**
     * 秒杀交换机
     */
    public static final String SK_EXCHANGE = "SK";
    /**
     * 秒杀队列
     */
    public static final String QUEUE= "Q";
    /**
     * 死信交换机
     */
    public static final String SK_DEAD_LETTER_EXCHANGE = "SK-D";
    /**
     * 死信队列
     */
    public static final String DEAD_LETTER_QUEUE = "DQ";
    /**
     * 备份交换机
     */
    public static final String BACKUP_EXCHANGE_NAME = "bkExchange";
    /**
     * 备份队列
     */
    public static final String BACKUP_QUEUE_NAME = "bkQueue";
    /**
     * 警告队列
     */
    public static final String WARNING_QUEUE_NAME = "warQueue";



    /**
     * 声明备份 Exchange
     */
    @Bean("bkExchange")
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 声明警告队列
     */
    @Bean("warQueue")
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE_NAME).build();
    }

    /**
     * 声明报警队列绑定关系
     */
    @Bean
    public Binding warningBinding(@Qualifier("warQueue") Queue queue,
                                  @Qualifier("bkExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(queue).to(backupExchange);
    }

    /**
     * 声明备份队列
     */
    @Bean("bkQueue")
    public Queue backQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE_NAME).build();
    }

    /**
     * 声明备份队列绑定关系
     */
    @Bean
    public Binding backupBinding(@Qualifier("bkQueue") Queue queue,
                                 @Qualifier("bkExchange") FanoutExchange backupExchange) {
        return BindingBuilder.bind(queue).to(backupExchange);
    }



    /**
     * 声明 秒杀交换机
     */
    @Bean("skExchange")
    public DirectExchange skExchange() {
        ExchangeBuilder exchangeBuilder = ExchangeBuilder.directExchange(SK_EXCHANGE)
                .durable(true)
                //设置该交换机的备份交换机
                .withArgument("alternate-exchange", BACKUP_EXCHANGE_NAME);
        return exchangeBuilder.build();
    }

    /**
     * 声明 死信队列交换机
     */
    @Bean("skDExchange")
    public DirectExchange skDExchange() {
        return new DirectExchange(SK_DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明队列 A ttl 为 10s 并绑定到对应的死信交换机
     */
    @Bean("queue")
    public Queue queue() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", SK_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", "DQ");
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE).withArguments(args).build();
    }

    /**
     * 声明队列 queue 绑定 SK 交换机
     */
    @Bean
    public Binding queueBindingSK(@Qualifier("queue") Queue queue,
                                  @Qualifier("skExchange") DirectExchange xExchange) {
        return BindingBuilder.bind(queue).to(xExchange).with("A");
    }


    /**
     * 声明死信队列 QD
     */
    @Bean("dQueue")
    public Queue queueD() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    /**
     * 声明死信队列 QD 绑定关系
     */
    @Bean
    public Binding deadLetterBindingSKD(@Qualifier("dQueue") Queue queueD,
                                        @Qualifier("skDExchange") DirectExchange yExchange) {
        return BindingBuilder.bind(queueD).to(yExchange).with("DQ");
    }

}
