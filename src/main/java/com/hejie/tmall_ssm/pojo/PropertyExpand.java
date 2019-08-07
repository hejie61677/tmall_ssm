package com.hejie.tmall_ssm.pojo;

/**
 * @Program: tmall_ssm
 * @Description: Property类的拓展类
 * @Author: hejie
 * @Create: 2019-06-25 17:03
 */
public class PropertyExpand extends Property {

    //分类
    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setProperty(Property property) {
        this.setName(property.getName());
        this.setId(property.getId());
        this.setCid(property.getCid());
    }
}
