package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Category;
import com.hejie.tmall_ssm.util.Page;
import java.util.List;

public interface CategoryService{
    int total();
    List<Category> list(Page page);

    void add(Category category);

    void delete(int id);

    Category get(int id);

    void update(Category category);
}