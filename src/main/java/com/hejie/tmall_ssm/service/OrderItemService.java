package com.hejie.tmall_ssm.service;

import com.hejie.tmall_ssm.pojo.Order;
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

    void add(OrderItem orderItem);

    void delete(int id);

    void update(OrderItem orderItem);

    OrderItem get(int id);

    OrderItemExpand getE(int id);

    List list();

    void fill(List<OrderExpand> orderExpands);

    void fill(OrderExpand orderExpand);

}
