package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Category;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: Category服务接口
  * @Author: hejie
  * @Create: 2019/6/25
  */
public interface CategoryService{

    List<Category> list();

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category category);
}