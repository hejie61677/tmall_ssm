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

    void add(ProductImage productImage);

    void delete(int id);

    void update(ProductImage productImage);

    ProductImage get(int id);

    List list(int pid, String type);

}
