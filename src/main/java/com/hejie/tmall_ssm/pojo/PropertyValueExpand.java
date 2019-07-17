package com.hejie.tmall_ssm.pojo;

/**
 * @program: tmall_ssm
 * @description: PropertyValue拓展类
 * @author: hejie
 * @create: 2019-07-12 11:21
 */
public class PropertyValueExpand extends PropertyValue {

    private Property property;

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public void setPropertyValue(PropertyValue propertyValue) {
        this.setId(propertyValue.getId());
        this.setPid(propertyValue.getPid());
        this.setPtid(propertyValue.getPtid());
        this.setValue(propertyValue.getValue());
    }
}
