package com.venky.tinet.service;

import com.venky.tinet.common.mybatis.service.BaseService;
import com.venky.tinet.mapper.IndexMapper;
import com.venky.tinet.model.bean.User;
import org.springframework.stereotype.Service;

/**
 * 服务层
 *
 * @author huwk
 * @date 2018/12/13
 **/
@Service
public class IndexService extends BaseService<IndexMapper, User> {
}
