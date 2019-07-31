package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Review;
import com.hejie.tmall_ssm.pojo.ReviewExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: Review服务接口
 * @Author: hejie
 * @Create: 2019/7/30
 */
public interface ReviewService {

    //新增评论
    void add(Review review);

    //删除评论
    void delete(int id);

    //修改评论
    void update(Review review);

    //获取评论
    Review get(int id);

    //获取评论列表
    List<Review> list(int pid);

    //获取评论拓展列表
    List<ReviewExpand> listE(int pid);

    //获取评论数
    int getCount(int pid);

}
