package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.CategoryExpand;
import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: Product服务接口
 * @Author: hejie
 * @Create: 2019/6/28
 */
public interface ProductService {

    List<Product> list(int cid);

    List<ProductExpand> listPe(int cid);

    void add(Product product);

    void delete(int id);

    Product get(int id);

    ProductExpand getPe(int id);

    void update(Product product);

    void setFirstProductImage(ProductExpand productExpand);

    void fill(List<CategoryExpand> categoryExpands);

    void fill(CategoryExpand categoryExpand);

    void fillByRow(List<CategoryExpand> categoryExpands);

}
