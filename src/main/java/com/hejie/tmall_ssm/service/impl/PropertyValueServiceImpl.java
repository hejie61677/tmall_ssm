package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.PropertyValueMapper;
import com.hejie.tmall_ssm.pojo.*;
import com.hejie.tmall_ssm.service.PropertyService;
import com.hejie.tmall_ssm.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: tmall_ssm
 * @description:
 * @author: hejie
 * @create: 2019-07-12 16:08
 */
@Service
public class PropertyValueServiceImpl implements PropertyValueService {

   @Autowired
    PropertyValueMapper propertyValueMapper;

   @Autowired
    PropertyService propertyService;

    @Override
    public void init(Product product) {
        List<Property> properties = propertyService.list(product.getCid());

        for (Property property : properties) {
            PropertyValue propertyValue = get(property.getId(), product.getId());
            if (propertyValue == null) {
                propertyValue = new PropertyValue();
                propertyValue.setPid(product.getId());
                propertyValue.setPtid(property.getId());
                propertyValueMapper.insert(propertyValue);
            }
        }
    }

    @Override
    public List<PropertyValue> list(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        return propertyValueMapper.selectByExample(propertyValueExample);
    }

    @Override
    public List<PropertyValueExpand> listE(int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);

        return getPVEList(propertyValues);
    }

    @Override
    public PropertyValue get(int ptid, int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);

        if (propertyValues.isEmpty()) {
            return null;
        } else {
            return propertyValues.get(0);
        }
    }

    @Override
    public PropertyValueExpand getE(int ptid, int pid) {
        PropertyValueExample propertyValueExample = new PropertyValueExample();
        propertyValueExample.createCriteria().andPtidEqualTo(ptid).andPidEqualTo(pid);
        List<PropertyValue> propertyValues = propertyValueMapper.selectByExample(propertyValueExample);

        if (propertyValues.isEmpty()) {
            return null;
        } else {
            PropertyValueExpand propertyValueExpand = new PropertyValueExpand();
            propertyValueExpand.setPropertyValue(propertyValues.get(0));
            propertyValueExpand.setProperty(propertyService.get(propertyValues.get(0).getPtid()));
            return propertyValueExpand;
        }
    }

    @Override
    public void update(PropertyValue propertyValue) {
        propertyValueMapper.updateByPrimaryKeySelective(propertyValue);
    }

    public List<PropertyValueExpand> getPVEList(List<PropertyValue> propertyValues) {
        List<PropertyValueExpand> propertyValueExpands = new ArrayList<>();
        PropertyValueExpand propertyValueExpand;

        for (PropertyValue propertyValue : propertyValues) {
            propertyValueExpand = new PropertyValueExpand();
            propertyValueExpand.setPropertyValue(propertyValue);
            propertyValueExpand.setProperty(propertyService.get(propertyValue.getPtid()));
            propertyValueExpands.add(propertyValueExpand);
        }

        return propertyValueExpands;
    }
}
