package com.venky.tinet.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * Mybatis的列名的注解
 *
 * @create by zhao.weiwei
 * @create on 2017年4月26日下午3:12:14
 * @email is zhao.weiwei@jyall.com.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyColumn {
    /**
     * 列名
     *
     * @return
     */
    String value() default "";

    /**
     * 模糊匹配查询是否精确查找，默认false
     *
     * @return
     */
    boolean accuracy() default false;
}
