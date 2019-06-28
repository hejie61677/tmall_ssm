package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.ProductExample;
import java.util.List;

public interface ProductMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Product record);

    int insertSelective(Product record);

    List<Product> selectByExample(ProductExample example);

    Product selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Product record);

    int updateByPrimaryKey(Product record);
}