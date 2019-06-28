package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.pojo.Review;
import com.hejie.tmall_ssm.pojo.ReviewExample;
import java.util.List;

public interface ReviewMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Review record);

    int insertSelective(Review record);

    List<Review> selectByExample(ReviewExample example);

    Review selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Review record);

    int updateByPrimaryKey(Review record);
}