package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Property;
import com.hejie.tmall_ssm.pojo.PropertyExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: Property服务接口
 * @Author: hejie
 * @Create: 2019/6/25
 */
public interface PropertyService {

    //获取属性列表
    List<Property> list(int cid);

    //新增属性
    void add(Property property);

    //删除属性
    void delete(int id);

    //获取属性
    Property get(int id);

    //获取属性拓展
    PropertyExpand getPe(int id);

    //修改属性
    void update(Property property);

}
