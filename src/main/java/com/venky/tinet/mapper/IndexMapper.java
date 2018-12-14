package com.venky.tinet.mapper;

import com.venky.tinet.common.mybatis.mapper.JYBaseMapper;
import com.venky.tinet.model.bean.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * mapper
 *
 * @author huwk
 * @date 2018/12/13
 **/
@Mapper
public interface IndexMapper extends JYBaseMapper<User> {
}
