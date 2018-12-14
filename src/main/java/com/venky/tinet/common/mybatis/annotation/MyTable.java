package com.venky.tinet.common.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mybatis的表名的注解
 *
 * @create by zhao.weiwei
 * @create on 2017年4月26日下午3:12:34
 * @email is zhao.weiwei@jyall.com.
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyTable {
	String value() default "";
}
