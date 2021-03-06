package com.hejie.tmall_ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hejie.tmall_ssm.pojo.User;
import com.hejie.tmall_ssm.service.UserService;
import com.hejie.tmall_ssm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: 用户管理前端控制器
 * @Author: hejie
 * @Create: 2019-07-18 10:01
 */
@Controller
@RequestMapping("")
public class UserController {

    @Autowired
    UserService userService;

    /**
      * @Description: 用户列表查询
      * @Author: hejie
      * @Date: 2019/7/18
      */
    @RequestMapping("admin_user_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<User> users = userService.list();
        int total = (int) new PageInfo<>(users).getTotal();
        page.setTotal(total);
        model.addAttribute("us", users);
        model.addAttribute("page", page);

        return  "admin/listUser";
    }

}
