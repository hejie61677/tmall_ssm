package com.hejie.tmall_ssm.pojo;

import java.util.List;

/**
 * @program: tmall_ssm
 * @description: Category拓展类
 * @author: hejie
 * @create: 2019-07-19 16:21
 */
public class CategoryExpand extends Category {

    /**
     *  20190802新增 构造函数，初始化类型对象
     */
    public CategoryExpand(Category category) {
        setCategory(category);
    }

    //产品集合(全部)
    private List<ProductExpand> products;

    //产品集合(根据行数获取)
    private List<List<ProductExpand>> productsByRow;

    public void setCategory(Category category) {
        this.setId(category.getId());
        this.setName(category.getName());
    }

    public List<ProductExpand> getProducts() {
        return products;
    }

    public void setProducts(List<ProductExpand> products) {
        this.products = products;
    }

    public List<List<ProductExpand>> getProductsByRow() {
        return productsByRow;
    }

    public void setProductsByRow(List<List<ProductExpand>> productsByRow) {
        this.productsByRow = productsByRow;
    }
}
