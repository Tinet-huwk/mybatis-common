package com.venky.tinet.model.bean;

import com.venky.tinet.common.mybatis.annotation.MyColumn;
import com.venky.tinet.common.mybatis.annotation.MyId;
import com.venky.tinet.common.mybatis.annotation.MyTable;

import java.util.Date;

/**
 * 测试实体类
 *
 * @author huwk
 * @date 2018/12/13
 **/
@MyTable("rms_user")
public class User {

    @MyId("id")
    private String  id;

    @MyColumn("user_name")
    private String userName;

    @MyColumn("password")
    private String password;

    @MyColumn("description")
    private String description;

    @MyColumn("create_time")
    private Date createTime;

    @MyColumn("mobile")
    private String mobile;

    @MyColumn("update_time")
    private Date updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

}
