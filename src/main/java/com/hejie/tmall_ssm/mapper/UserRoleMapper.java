package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.UserRole;
import com.hejie.tmall_ssm.pojo.UserRoleExample;
import java.util.List;

public interface UserRoleMapper {
    int insert(UserRole record);

    int insertSelective(UserRole record);

    List<UserRole> selectByExample(UserRoleExample example);
}