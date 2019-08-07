package com.hejie.tmall_ssm.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hejie.tmall_ssm.pojo.Order;
import com.hejie.tmall_ssm.pojo.OrderExpand;
import com.hejie.tmall_ssm.service.OrderItemService;
import com.hejie.tmall_ssm.service.OrderService;
import com.hejie.tmall_ssm.util.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: 订单管理前端控制器
 * @Author: hejie
 * @Create: 2019-07-18 17:04
 */
@Controller
@RequestMapping("")
public class OrderController {

    @Autowired
    OrderService orderService;

    @Autowired
    OrderItemService orderItemService;

    /**
     * @Description: 订单列表查询
     * @Author: hejie
     * @date: 2019/7/18
     */
    @RequestMapping("admin_order_list")
    public String list(Model model, Page page) {
        PageHelper.offsetPage(page.getStart(), page.getCount());
        List<OrderExpand> orderExpands = orderService.list();
        int total = (int) new PageInfo<>(orderExpands).getTotal();
        page.setTotal(total);
        orderItemService.fill(orderExpands);
        model.addAttribute("os", orderExpands);
        model.addAttribute("page", page);

        return "admin/listOrder";
    }

    /**
     * @Description: 商品发货
     * @Author: hejie
     * @date: 2019/7/18
     */
    @RequestMapping("admin_order_delivery")
    public String delivery(Order order) {
        order.setDelivery_date(new Date());
        order.setStatus(OrderService.waitConfirm);
        orderService.update(order);

        return "redirect:admin_order_list";
    }

}
