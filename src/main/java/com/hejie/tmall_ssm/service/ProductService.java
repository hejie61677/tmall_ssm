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

    //列表查询-产品
    List<Product> list(int cid);

    //列表查询-产品拓展
    List<ProductExpand> listPe(int cid);

    //新增产品
    void add(Product product);

    //删除产品
    void delete(int id);

    //获取单个产品信息
    Product get(int id);

    //获取单个产品拓展信息
    ProductExpand getPe(int id);

    //更新产品
    void update(Product product);

    //设置产品主图片
    void setFirstProductImage(ProductExpand productExpand);

    //填充分类信息-全部
    void fill(List<CategoryExpand> categoryExpands);

    //填充分类信息-单个
    void fill(CategoryExpand categoryExpand);

    //填充分类信息-根据row数
    void fillByRow(List<CategoryExpand> categoryExpands);

    //设置销量和评论数量-单个
    void setSaleAndReview(ProductExpand productExpand);

    //设置销量和评论数量-多个
    void setSalesAndReviews(List<ProductExpand> productExpands);
}
