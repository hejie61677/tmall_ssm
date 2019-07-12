package com.hejie.tmall_ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hejie.tmall_ssm.pojo.Category;
import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.ProductExpand;
import com.hejie.tmall_ssm.service.CategoryService;
import com.hejie.tmall_ssm.service.ProductService;
import com.hejie.tmall_ssm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: 产品管理控制器
  * @Author: hejie
  * @Create: 2019/6/28
  */
@Controller
@RequestMapping("")
public class ProductController {

    @Autowired CategoryService categoryService;

    @Autowired ProductService productService;

    /**
     * @Description: 产品列表查询
     * @Author: hejie
     * @date: 2019/6/28
     */
    @RequestMapping("admin_product_list")
    public String list(int cid, Model model, Page page) {
        Category category = categoryService.get(cid);
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<ProductExpand> products = productService.listPe(cid);
        int total = (int) new PageInfo<>(products).getTotal();
        page.setTotal(total);
        page.setParam("&cid=" + category.getId());
        model.addAttribute("c", category);
        model.addAttribute("ps", products);
        model.addAttribute("page", page);
        return "admin/listProduct";
    }

    /**
     * @Description: 产品新增
     * @Author: hejie
     * @date: 2019/6/28
     */
    @RequestMapping("admin_product_add")
    public String add(Product product){
        product.setCreate_date(new Date());
        productService.add(product);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    /**
     * @Description: 产品删除
     * @Author: hejie
     * @date: 2019/6/28
     */
    @RequestMapping("admin_product_delete")
    public String delete(int id) {
        Product product = productService.get(id);
        productService.delete(id);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

    /**
     * @Description: 产品修改前查询
     * @Author: hejie
     * @date: 2019/6/28
     */
    @RequestMapping("admin_product_edit")
    public String edit(int id, Model model) {
        ProductExpand productExpand = productService.getPe(id);
        Category category = categoryService.get(productExpand.getCid());
        productExpand.setCategory(category);
        model.addAttribute("p", productExpand);
        return "admin/editProduct";
    }

    /**
      * @Description: 产品修改
      * @Author: hejie
      * @date: 2019/6/28
      */
    @RequestMapping("admin_product_update")
    public String update(Product product) {
        productService.update(product);
        return "redirect:/admin_product_list?cid=" + product.getCid();
    }

}