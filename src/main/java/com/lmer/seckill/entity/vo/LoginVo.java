/**
 * @program: seckill
 * @description: 登录vo
 * @author: Lmer
 * @create: 2022-03-21 21:27
 **/
package com.lmer.seckill.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class LoginVo {
    private String token;
    private String username;
}
