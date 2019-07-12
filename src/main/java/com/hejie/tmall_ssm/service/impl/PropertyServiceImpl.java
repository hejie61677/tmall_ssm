package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.CategoryMapper;
import com.hejie.tmall_ssm.mapper.PropertyMapper;
import com.hejie.tmall_ssm.pojo.Property;
import com.hejie.tmall_ssm.pojo.PropertyExample;
import com.hejie.tmall_ssm.pojo.PropertyExpand;
import com.hejie.tmall_ssm.service.PropertyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: Property接口实现类
  * @Author: hejie
  * @Create: 2019/6/25
  */
@Service
public class PropertyServiceImpl implements PropertyService {

    @Autowired
    PropertyMapper propertyMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Property> list(int cid) {
        PropertyExample propertyExample = new PropertyExample();
        propertyExample.createCriteria().andCidEqualTo(cid);
        propertyExample.setOrderByClause("id asc");
        return propertyMapper.selectByExample(propertyExample);
    }

    @Override
    public void add(Property property) {
        propertyMapper.insert(property);
    }

    @Override
    public void delete(int id) {
        propertyMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Property get(int id) {
        return propertyMapper.selectByPrimaryKey(id);
    }

    @Override
    public PropertyExpand getPe(int id) {
        Property property = propertyMapper.selectByPrimaryKey(id);
        PropertyExpand propertyExpand = new PropertyExpand();
        propertyExpand.setCategory(categoryMapper.selectByPrimaryKey(property.getCid()));
        propertyExpand.setProperty(property);
        return propertyExpand;
    }

    @Override
    public void update(Property property) {
        propertyMapper.updateByPrimaryKey(property);
    }
}