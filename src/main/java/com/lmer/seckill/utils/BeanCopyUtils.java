/**
 * @program: LBolg
 * @description: bean拷贝工具类
 * @author: Lmer
 * @create: 2022-03-20 11:52
 **/
package com.lmer.seckill.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class BeanCopyUtils {


    /**
     * 单个bean的拷贝
     * @param source 源类
     * @param clazz 对象类的类型
     * @return 新bean
     */
    public static <V> V copy(Object source, Class<V> clazz){
        V target = null;

        try {
            target = clazz.newInstance();
        }catch (Exception e) {
            e.printStackTrace();
        }

        BeanUtils.copyProperties(source, Objects.requireNonNull(target));
        return target;
    }

    /**
     * bean集合拷贝
     * @param source 源集合
     * @param clazz 对象类的类型
     * @return 新bean集合
     */
    public static <E> List<E> copyList(List<? extends Object> source, Class<E> clazz){
        return source.stream()
                .map(s -> copy(s, clazz))
                .collect(Collectors.toList());
    }


}
