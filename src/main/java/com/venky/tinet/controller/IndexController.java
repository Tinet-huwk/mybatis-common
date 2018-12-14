package com.venky.tinet.controller;

import com.venky.tinet.model.ApiResponseEntity;
import com.venky.tinet.model.bean.User;
import com.venky.tinet.service.IndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 测试控制器
 *
 * @author huwk
 * @date 2018/12/13
 **/
@RestController
@RequestMapping("/api")
public class IndexController {

    @Autowired
    IndexService indexService;

    @GetMapping
    public ApiResponseEntity list() {
        User user = indexService.getById("1");
        return ApiResponseEntity.ok("user",user).build();
    }

    @PostMapping
    public ApiResponseEntity add(){
        User user = new User();
        user.setCreateTime(new Date());
        user.setDescription("哈哈哈");
        user.setMobile("188998");
        user.setPassword("ddfjdfdfdfdfdf");
        user.setUserName("胡");
        user.setUpdateTime(new Date());


        indexService.add(user);
        return ApiResponseEntity.ok("user",user).build();
    }


}
