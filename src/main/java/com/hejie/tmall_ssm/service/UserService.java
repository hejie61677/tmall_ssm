package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.pojo.UserExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: User服务接口
 * @Author: hejie
 * @Create: 2019/7/17
 */
public interface UserService {

    //新增用户
    void add(User user);

    //删除用户
    void delete(int id);

    //修改用户
    void update(User user);

    //获取单个用户
    User get(int id);

    //获取单个用户-拓展
    UserExpand getE(int id);

    //获取用户列表
    List list();

    //判断用户名是否存在
    boolean isExist(String name);

    //获取单个用户
    User get(String name, String password);

}
