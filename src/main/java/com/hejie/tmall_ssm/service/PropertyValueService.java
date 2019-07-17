package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.PropertyValue;
import com.hejie.tmall_ssm.pojo.PropertyValueExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: PropertyValue服务接口
 * @Author: hejie
 * @Create: 2019/7/12
 */
public interface PropertyValueService {

    void init(Product product);

    List<PropertyValue> list(int pid);

    List<PropertyValueExpand> listE(int pid);

    PropertyValue get(int ptid, int pid);

    PropertyValueExpand getE(int ptid, int pid);

    void update(PropertyValue propertyValue);

}
