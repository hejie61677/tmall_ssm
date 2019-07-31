package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.ProductImage;

import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: ProductImage服务接口
  * @Author: hejie
  * @Create: 2019/7/4
  */
public interface ProductImageService {

    //单个图片
    String type_single = "type_single";

    //详情图片
    String type_detail = "type_detail";

    //新增
    void add(ProductImage productImage);

    //删除
    void delete(int id);

    //修改
    void update(ProductImage productImage);

    //获取产品图片
    ProductImage get(int id);

    //获取产品图片列表
    List<ProductImage> list(int pid, String type);

}
