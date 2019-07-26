package com.hejie.tmall_ssm.controller;

import com.hejie.tmall_ssm.pojo.CategoryExpand;
import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @program: tmall_ssm
 * @description: 前端相关管理
 * @author: hejie
 * @create: 2019-07-22 09:30
 */
@Controller
@RequestMapping("")
public class ForeController {

    @Autowired
    CategoryService categoryService;

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    PropertyValueService propertyValueService;

    @Autowired OrderService orderService;

    @Autowired OrderItemService orderItemService;

    /**
      * @Description: 前端主页面
      * @Author: hejie
      * @Date: 2019/7/22
      */
    @RequestMapping("forehome")
    public String home(Model model) {
        List<CategoryExpand> categoryExpands = categoryService.listE();
        productService.fill(categoryExpands);
        productService.fillByRow(categoryExpands);
        model.addAttribute("cs", categoryExpands);
        return "fore/home";
    }

    /**
      * @Description: 用户注册
      * @Author: hejie
      * @Date: 2019/7/25
      */
    @RequestMapping("foreregister")
    public String register(Model model, User user) {
        String name = user.getName();
        name = HtmlUtils.htmlEscape(name);
        user.setName(name);
        boolean exist = userService.isExist(name);

        if (exist) {
            String msg = "用户名已经存在，无法使用";
            model.addAttribute("msg", msg);
            model.addAttribute("user", null);
            return "fore/register";
        }

        userService.add(user);

        return "redirect:registerSuccessPage";
    }

    /**
     * @Description: 用户登录
     * @Author: hejie
     * @Date: 2019/7/26
     */
    @RequestMapping("forelogin")
    public String login(@RequestParam("name") String name, @RequestParam("password") String password, Model model, HttpSession session) {
        name = HtmlUtils.htmlEscape(name);
        User user = userService.get(name, password);

        if (null == user) {
            model.addAttribute("msg", "账号密码错误");
            return "fore/login";
        } else {
            session.setAttribute("user", user);
            return "redirect:forehome";
        }
    }

}
