package com.lmer.seckill.entity;


import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.experimental.Accessors;

/**
 * (SuccessUser)表实体类
 *
 * @author Lmer
 * @since 2022-03-21 20:21:27
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
@TableName("success_user")
public class SuccessUser implements Serializable {
    @TableId
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
    
    /**
     * 产品id
     */
    private Long proId;
    



}

