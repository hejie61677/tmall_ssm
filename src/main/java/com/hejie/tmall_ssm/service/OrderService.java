package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Order;
import com.hejie.tmall_ssm.pojo.OrderExpand;
import com.hejie.tmall_ssm.pojo.OrderItemExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: Order服务接口
 * @Author: hejie
 * @Create: 2019/7/18
 */
public interface OrderService {

    String waitPay = "waitPay";   //待付款
    String waitDelivery = "waitDelivery";   //待发货
    String waitConfirm= "waitConfirm";   //待收货
    String waitReview = "waitReview";   //待评价
    String finish = "finish";   //完成
    String delete = "delete";   //刪除

    //新增
    void add(Order order);

    //新增-根据已存在(已添加购物车,但未下单)订单项
    float add(Order order, List<OrderItemExpand> orderItemExpands);

    //删除
    void delete(int id);

    //更新
    void update(Order order);

    //获取单个订单
    Order get(int id);

    //获取单个订单（拓展）
    OrderExpand getE(int id);

    //获取订单列表
    List<OrderExpand> list();

    //获取订单列表-根据具体用户和订单状态
    List<OrderExpand> list(int uid, String excludedStatus);

}
