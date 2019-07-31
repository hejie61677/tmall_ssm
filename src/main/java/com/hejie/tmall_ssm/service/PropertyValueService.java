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

    //根据产品初始化属性值
    void init(Product product);

    //获取属性值列表
    List<PropertyValue> list(int pid);

    //获取属性值拓展列表
    List<PropertyValueExpand> listE(int pid);

    //获取单个属性值
    PropertyValue get(int ptid, int pid);

    //获取单个拓展属性值
    PropertyValueExpand getE(int ptid, int pid);

    //修改属性值
    void update(PropertyValue propertyValue);

}
