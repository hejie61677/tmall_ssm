package com.hejie.tmall_ssm.pojo;

/**
 * @program: tmall_ssm
 * @description: Product类的拓展类
 * @author: hejie
 * @create: 2019-06-28
 */
public class ProductExpand extends Product {

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
