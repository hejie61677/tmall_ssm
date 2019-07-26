package com.hejie.tmall_ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @program: tmall_ssm
 * @description: 服务端跳转管理类
 * @author: hejie
 * @create: 2019-07-25 10:43
 */
@Controller
@RequestMapping("")
public class PageController {

    @RequestMapping("registerPage")
    public String registerPage() {
        return "fore/register";
    }

    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }

    @RequestMapping("loginPage")
    public String loginPage() {
        return "fore/login";
    }

    @RequestMapping("foreAlipay")
    public String foreAlipay() {
        return "fore/alipay";
    }


}
