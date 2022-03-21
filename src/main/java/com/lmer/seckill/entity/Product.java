package com.lmer.seckill.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (Product)表实体类
 *
 * @author Lmer
 * @since 2022-03-21 20:18:19
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("product")
public class Product  {
    @TableId
    private Integer id;

    /**
     * 产品名字
     */
    private String name;

    /**
     * 产品价格
     */
    private Integer price;

    /**
     * 产品数量
     */
    private Integer number;

    /**
     * 为1是已经开始秒杀,为0是已经结束或还没开始
     */
    private String secStatus;



}

