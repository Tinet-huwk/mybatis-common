package com.venky.tinet.configuration;

import com.venky.tinet.common.mybatis.service.IBaseUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 用户配置类
 *
 * @author huwk
 * @date 2018/12/13
 **/
@Configuration
public class UserServiceConfiguration {

    @Primary
    @Bean
    public IBaseUserService userService(){

        return () -> "1";
    }
}
