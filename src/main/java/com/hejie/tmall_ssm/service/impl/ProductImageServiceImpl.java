package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.ProductImageMapper;
import com.hejie.tmall_ssm.pojo.ProductImage;
import com.hejie.tmall_ssm.pojo.ProductImageExample;
import com.hejie.tmall_ssm.service.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: ProductImage接口实现类
  * @Author: hejie
  * @Create: 2019/7/4
  */
@Service
public class ProductImageServiceImpl implements ProductImageService {

    @Autowired
    ProductImageMapper productImageMapper;

    @Override
    public List list(int pid, String type) {
        ProductImageExample productImageExample = new ProductImageExample();
        productImageExample.createCriteria().andPidEqualTo(pid).andTypeEqualTo(type);
        productImageExample.setOrderByClause("id asc");
        return productImageMapper.selectByExample(productImageExample);
    }

    @Override
    public void add(ProductImage productImage) {
        productImageMapper.insert(productImage);
    }

    @Override
    public void delete(int id) {
        productImageMapper.deleteByPrimaryKey(id);
    }

    @Override
    public ProductImage get(int id) {
        return productImageMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(ProductImage productImage) {
        productImageMapper.updateByPrimaryKey(productImage);
    }
}