package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Category;
import com.hejie.tmall_ssm.pojo.CategoryExpand;

import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: Category服务接口
  * @Author: hejie
  * @Create: 2019/6/25
  */
public interface CategoryService{
    //列表查询-分类
    List<Category> list();

    //列表查询-分类拓展
    List<CategoryExpand> listE();

    //新增分类
    void add(Category category);

    //删除分类
    void delete(int id);

    //获取单个分类
    Category get(int id);

    //更新分类
    void update(Category category);
}