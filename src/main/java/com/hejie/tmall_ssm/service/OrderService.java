package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Order;
import com.hejie.tmall_ssm.pojo.OrderExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: Order服务接口
 * @Author: hejie
 * @Create: 2019/7/18
 */
public interface OrderService {

    String waitPay = "waitPay";
    String waitDelivery = "waitDelivery";
    String waitConfirm= "waitConfirm";
    String waitReview = "waitReview";
    String finish = "finish";
    String delete = "delete";

    void add(Order order);

    void delete(int id);

    void update(Order order);

    Order get(int id);

    OrderExpand getE(int id);

    List list();

}
