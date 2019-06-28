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

    List<Property> list(int cid);

    void add(Property property);

    void delete(int id);

    Property get(int id);

    PropertyExpand getPe(int id);

    void update(Property category);

}
