package com.hejie.tmall_ssm.mapper;

import com.hejie.tmall_ssm.util.Page;
import com.hejie.tmall_ssm.pojo.Category;

import java.util.List;

public interface CategoryMapper {
     List<Category> list(Page page);

     int total();

     void add(Category category);

     void delete(int id);

     Category get(int id);

     void update(Category category);
}