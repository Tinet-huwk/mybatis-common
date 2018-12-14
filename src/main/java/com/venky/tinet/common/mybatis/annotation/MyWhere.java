package com.venky.tinet.common.mybatis.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Mybatis的条件的注解
 *
 * @create by zhao.liang
 * @create on 2017年5月16日下午3:12:34
 * @email is zhao.liang@jyall.com
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MyWhere {
	String value() default "";
}
