package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.pojo.UserExample;
import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}