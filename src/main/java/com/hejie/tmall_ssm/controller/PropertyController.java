package com.hejie.tmall_ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hejie.tmall_ssm.pojo.Category;
import com.hejie.tmall_ssm.pojo.Property;
import com.hejie.tmall_ssm.pojo.PropertyExpand;
import com.hejie.tmall_ssm.service.CategoryService;
import com.hejie.tmall_ssm.service.PropertyService;
import com.hejie.tmall_ssm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: 商品分类属性管理控制器
  * @Author: hejie
  * @Create: 2019/6/26
  */
@Controller
@RequestMapping("")
public class PropertyController {

    @Autowired CategoryService categoryService;

    @Autowired PropertyService propertyService;

    /**
      * @Description: 商品分类属性列表查询
      * @Author: hejie
      * @date: 2019/6/26
      */
    @RequestMapping("admin_property_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<Property> properties = propertyService.list(cid);
        int total = (int) new PageInfo<>(properties).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());
        model.addAttribute("c", category);
        model.addAttribute("ps", properties);
        model.addAttribute("page", page);
        return "admin/listProperty";
    }

    /**
     * @Description: 商品类别属性新增
     * @Author: hejie
     * @date: 2019/6/26
     */
    @RequestMapping("admin_property_add")
    public String add(Property property){
        propertyService.add(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    /**
     * @Description: 商品类别属性删除
     * @Author: hejie
     * @date: 2019/6/26
     */
    @RequestMapping("admin_property_delete")
    public String delete(int id) {
        Property property = propertyService.get(id);
        propertyService.delete(id);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

    /**
     * @Description: 商品类别属性修改前查询
     * @Author: hejie
     * @date: 2019/6/26
     */
    @RequestMapping("admin_property_edit")
    public String edit(int id, Model model) {
        PropertyExpand propertyExpand = propertyService.getPe(id);
        Category category = categoryService.get(propertyExpand.getCid());
        propertyExpand.setCategory(category);
        model.addAttribute("p", propertyExpand);
        return "admin/editProperty";
    }

    /**
      * @Description: 商品类别属性修改
      * @Author: hejie
      * @date: 2019/6/26
      */
    @RequestMapping("admin_property_update")
    public String update(Property property) {
        propertyService.update(property);
        return "redirect:/admin_property_list?cid=" + property.getCid();
    }

}