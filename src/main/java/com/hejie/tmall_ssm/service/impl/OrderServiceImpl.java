package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.OrderMapper;
import com.hejie.tmall_ssm.pojo.Order;
import com.hejie.tmall_ssm.pojo.OrderExample;
import com.hejie.tmall_ssm.pojo.OrderExpand;
import com.hejie.tmall_ssm.service.OrderService;
import com.hejie.tmall_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: tmall_ssm
 * @description: Order接口实现类
 * @author: hejie
 * @create: 2019-07-18 16:33
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    public void delete(int id) {
        orderMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(Order order) {
        orderMapper.updateByPrimaryKeySelective(order);
    }

    @Override
    public Order get(int id) {
        return orderMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrderExpand getE(int id) {
        OrderExpand orderExpand = new OrderExpand();
        orderExpand.setOrder(orderMapper.selectByPrimaryKey(id));
        return orderExpand;
    }

    @Override
    public List list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id asc");
        List<Order> orders = orderMapper.selectByExample(orderExample);
        List<OrderExpand> orderExpands = new ArrayList<>();

        for (Order order : orders) {
            orderExpands.add(getE(order.getId()));
        }

        setUser(orderExpands);

        return orderExpands;
    }

    public void setUser(List<OrderExpand> orderExpands) {
        for (OrderExpand orderExpand : orderExpands) {
            setUser(orderExpand);
        }
    }

    public void setUser(OrderExpand orderExpand) {
        orderExpand.setUser(userService.get(orderExpand.getUid()));
    }
}
