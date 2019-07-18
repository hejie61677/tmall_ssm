package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.User;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: User服务接口
 * @Author: hejie
 * @Create: 2019/7/17
 */
public interface UserService {

    void add(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List list();

}
