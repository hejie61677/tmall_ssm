package com.hejie.tmall_ssm.controller;

import com.hejie.tmall_ssm.pojo.Product;
import com.hejie.tmall_ssm.pojo.ProductExpand;
import com.hejie.tmall_ssm.pojo.PropertyValue;
import com.hejie.tmall_ssm.pojo.PropertyValueExpand;
import com.hejie.tmall_ssm.service.ProductService;
import com.hejie.tmall_ssm.service.PropertyValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @program: tmall_ssm
 * @description: PropertyValue前端控制器
 * @author: hejie
 * @create: 2019-07-15 11:10
 */
@Controller
@RequestMapping("")
public class PropertyValueController {

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired
    ProductService productService;

    /**
      * @Description: 编辑propertyValue
      * @Author: hejie
      * @Date: 2019/7/15
      */
    @RequestMapping("admin_propertyValue_edit")
    public String edit(Model model, int pid) {
        ProductExpand productExpand = productService.getPe(pid);
        Product product = productService.get(pid);
        propertyValueService.init(product);
        List<PropertyValueExpand> propertyValues = propertyValueService.listE(product.getId());
        model.addAttribute("p", productExpand);
        model.addAttribute("pvs", propertyValues);

        return "admin/editPropertyValue";
    }

    /**
      * @Description: 修改，返回结果不会被解析为跳转路径，而是直接写入HTTP response body中
      * @Author: hejie
      * @Date: 2019/7/15
      */
    @RequestMapping("admin_propertyValue_update")
    @ResponseBody
    public String update(PropertyValue propertyValue) {
        propertyValueService.update(propertyValue);

        return "success";
    }
}
