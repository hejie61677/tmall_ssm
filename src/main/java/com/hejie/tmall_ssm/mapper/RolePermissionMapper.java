package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.RolePermission;
import com.hejie.tmall_ssm.pojo.RolePermissionExample;
import java.util.List;

public interface RolePermissionMapper {
    int insert(RolePermission record);

    int insertSelective(RolePermission record);

    List<RolePermission> selectByExample(RolePermissionExample example);
}