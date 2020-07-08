package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.Role;
import com.hejie.tmall_ssm.pojo.RoleExample;
import java.util.List;

public interface RoleMapper {
    int insert(Role record);

    int insertSelective(Role record);

    List<Role> selectByExample(RoleExample example);
}