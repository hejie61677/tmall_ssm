package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.ProductImage;
import com.hejie.tmall_ssm.pojo.ProductImageExample;
import java.util.List;

public interface ProductImageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ProductImage record);

    int insertSelective(ProductImage record);

    List<ProductImage> selectByExample(ProductImageExample example);

    ProductImage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ProductImage record);

    int updateByPrimaryKey(ProductImage record);
}