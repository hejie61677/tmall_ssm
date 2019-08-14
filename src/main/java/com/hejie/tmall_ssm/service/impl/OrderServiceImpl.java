package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.OrderMapper;
import com.hejie.tmall_ssm.pojo.*;
import com.hejie.tmall_ssm.service.OrderItemService;
import com.hejie.tmall_ssm.service.OrderService;
import com.hejie.tmall_ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: Order接口实现类
 * @Author: hejie
 * @Create: 2019-07-18 16:33
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    UserService userService;

    @Autowired
    OrderItemService orderItemService;

    @Override
    public void add(Order order) {
        orderMapper.insert(order);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackForClassName = "Exception")
    public float add(Order order, List<OrderItemExpand> orderItemExpands) {
        float total = 0;
        add(order);

        if (false) {throw new RuntimeException();} //模拟测试执行异常回滚

        for (OrderItemExpand orderItemExpand : orderItemExpands) {
            orderItemExpand.setOid(order.getId());
            orderItemService.update(orderItemExpand);
            total += orderItemExpand.getProduct().getPromote_price() * orderItemExpand.getNumber();
        }

        return total;
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
    public List<OrderExpand> list() {
        OrderExample orderExample = new OrderExample();
        orderExample.setOrderByClause("id desc");
        return getOEbyO(orderMapper.selectByExample(orderExample));
    }

    @Override
    public List<OrderExpand> list(int uid, String excludedStatus) {
        OrderExample orderExample = new OrderExample();
        orderExample.createCriteria().andUidEqualTo(uid).andStatusNotEqualTo(excludedStatus);
        orderExample.setOrderByClause("id desc");

        return getOEbyO(orderMapper.selectByExample(orderExample));
    }

    public void setUser(List<OrderExpand> orderExpands) {
        for (OrderExpand orderExpand : orderExpands) {
            setUser(orderExpand);
        }
    }

    public void setUser(OrderExpand orderExpand) {
        orderExpand.setUser(userService.get(orderExpand.getUid()));
    }

    /**
     *  根据订单集合获取订单拓展集合
     */
    public List<OrderExpand> getOEbyO (List<Order> orders) {
        List<OrderExpand> orderExpands = new ArrayList<>();

        for (Order order : orders) {
            orderExpands.add(getE(order.getId()));
        }

        setUser(orderExpands);

        return orderExpands;
    }
}
