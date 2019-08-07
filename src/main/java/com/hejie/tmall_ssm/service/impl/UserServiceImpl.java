package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.UserMapper;
import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.pojo.UserExample;
import com.hejie.tmall_ssm.pojo.UserExpand;
import com.hejie.tmall_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: User接口实现类
 * @Author: hejie
 * @Create: 2019-07-18 09:10
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
    public UserExpand getE(int id) {
        UserExpand userExpand = new UserExpand();
        userExpand.setUser(userMapper.selectByPrimaryKey(id));

        return userExpand;
    }

    @Override
    public List list() {
        UserExample userExample = new UserExample();
        userExample.setOrderByClause("id asc");
        return userMapper.selectByExample(userExample);
    }

    @Override
    public boolean isExist(String name) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name);
        List<User> users = userMapper.selectByExample(userExample);

        if (users.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    @Override
    public User get(String name, String password) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andNameEqualTo(name).andPasswordEqualTo(password);
        List<User> users = userMapper.selectByExample(userExample);

        if (users.isEmpty()) {
            return null;
        } else {
            return users.get(0);
        }
    }

}
