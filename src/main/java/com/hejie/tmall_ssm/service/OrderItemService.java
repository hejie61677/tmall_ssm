package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.OrderExpand;
import com.hejie.tmall_ssm.pojo.OrderItem;
import com.hejie.tmall_ssm.pojo.OrderItemExpand;

import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: OrderItem服务接口
 * @Author: hejie
 * @Create: 2019/7/18
 */
public interface OrderItemService {

    //新增订单项
    void add(OrderItem orderItem);

    //删除订单项
    void delete(int id);

    //更新订单项
    void update(OrderItem orderItem);

    //获取订单项
    OrderItem get(int id);

    //获取订单项拓展
    OrderItemExpand getE(int id);

    //查询列表
    List list();

    //填充订单属性-多个订单
    void fill(List<OrderExpand> orderExpands);

    //填充订单属性-单个订单
    void fill(OrderExpand orderExpand);

    //获取销售量
    int getSales(int pid);

}
