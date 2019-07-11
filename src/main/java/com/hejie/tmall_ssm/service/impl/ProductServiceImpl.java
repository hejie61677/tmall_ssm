package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.CategoryMapper;
import com.hejie.tmall_ssm.mapper.ProductMapper;
import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.ProductExample;
import com.hejie.tmall_ssm.pojo.ProductExpand;
import com.hejie.tmall_ssm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: Product接口实现类
  * @Author: hejie
  * @Create: 2019/6/28
  */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id asc");
        return productMapper.selectByExample(productExample);
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(int id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProductExpand getPe(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        ProductExpand productExpand = new ProductExpand();
        productExpand.setCategory(categoryMapper.selectByPrimaryKey(product.getCid()));
        productExpand.setCid(product.getCid());
        productExpand.setId(product.getId());
        productExpand.setName(product.getName());
        productExpand.setCreate_date(product.getCreate_date());
        productExpand.setOriginal_price(product.getOriginal_price());
        productExpand.setPromote_price(product.getPromote_price());
        productExpand.setStock(product.getStock());
        productExpand.setSub_title(product.getSub_title());
        return productExpand;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }
}