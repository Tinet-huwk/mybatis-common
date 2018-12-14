package com.venky.tinet;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Hello world!
 *
 * @author huwk
 * @date 2018/12/13
 */
@EnableAutoConfiguration
@EnableTransactionManagement
@ComponentScan("com.venky.tinet")
@MapperScan("com.venky.tinet")
public class Application {

    public static void main(String[] args) {
        // 设置JVM的DNS缓存时间，详见：http://docs.amazonaws.cn/AWSSdkDocsJava/latest/DeveloperGuide/java-dg-jvm-ttl.html
        java.security.Security.setProperty("networkaddress.cache.ttl", "60");

        SpringApplication.run(Application.class, args);
    }
}
