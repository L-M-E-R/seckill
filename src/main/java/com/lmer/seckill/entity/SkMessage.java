/**
 * @program: seckill
 * @description: 秒杀队列消息
 * @author: Lmer
 * @create: 2022-03-21 22:28
 **/
package com.lmer.seckill.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class SkMessage {
    private Long userId;
    private Long proId;
}
