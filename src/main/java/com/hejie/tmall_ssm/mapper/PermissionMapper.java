package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.Permission;
import com.hejie.tmall_ssm.pojo.PermissionExample;
import java.util.List;

public interface PermissionMapper {
    int insert(Permission record);

    int insertSelective(Permission record);

    List<Permission> selectByExample(PermissionExample example);
}