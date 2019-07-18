package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.UserMapper;
import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.pojo.UserExample;
import com.hejie.tmall_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: tmall_ssm
 * @description: User接口实现类
 * @author: hejie
 * @create: 2019-07-18 09:10
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public void add(User user) {
        userMapper.insert(user);
    }

    @Override
    public void delete(int id) {
        userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User get(int id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id asc");
        return userMapper.selectByExample(userExample);
    }
}
