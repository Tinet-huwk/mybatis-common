package com.venky.tinet.common.mybatis.annotation;

import java.lang.annotation.*;

/**
 * mybatis的ID主键的注解
 *
 * @create by zhao.weiwei
 * @create on 2017年4月26日下午3:12:24
 * @email is zhao.weiwei@jyall.com.
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyId {
    String value() default "";
}
