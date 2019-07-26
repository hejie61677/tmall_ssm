package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.CategoryMapper;
import com.hejie.tmall_ssm.pojo.CategoryExample;
import com.hejie.tmall_ssm.pojo.Category;
import com.hejie.tmall_ssm.pojo.CategoryExpand;
import com.hejie.tmall_ssm.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: Category接口实现类
  * @Author: hejie
  * @Create: 2019/6/25
  */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired CategoryMapper categoryMapper;

    @Override
    public List<Category> list() {
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("id asc");
        return categoryMapper.selectByExample(categoryExample);
    }

    @Override
    public List<CategoryExpand> listE() {
        List<Category> categories = list();
        List<CategoryExpand> categoryExpands = new ArrayList<>();
        CategoryExpand categoryExpand;

        for (Category category : categories) {
            categoryExpand = new CategoryExpand();
            categoryExpand.setCategory(category);
            categoryExpands.add(categoryExpand);
        }

        return categoryExpands;
    }

    @Override
    public void add(Category category) {
        categoryMapper.insert(category);
    }

    @Override
    public void delete(int id) {
        categoryMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Category get(int id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public void update(Category category) {
        categoryMapper.updateByPrimaryKey(category);
    }
}