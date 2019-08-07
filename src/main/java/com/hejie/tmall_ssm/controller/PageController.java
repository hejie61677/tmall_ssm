package com.hejie.tmall_ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Program: tmall_ssm
 * @Description: 服务端跳转管理类
 * @Author: hejie
 * @Create: 2019-07-25 10:43
 */
@Controller
@RequestMapping("")
public class PageController {

    /**
     *  注册页面
     */
    @RequestMapping("registerPage")
    public String registerPage() {
        return "fore/register";
    }

    /**
     *  注册成功页面
     */
    @RequestMapping("registerSuccessPage")
    public String registerSuccessPage() {
        return "fore/registerSuccess";
    }

    /**
     *  登录页面
     */
    @RequestMapping("loginPage")
    public String loginPage() {
        return "fore/login";
    }

    /**
     *  支付页面
     */
    @RequestMapping("foreAlipay")
    public String foreAlipay() {
        return "fore/alipay";
    }


}
