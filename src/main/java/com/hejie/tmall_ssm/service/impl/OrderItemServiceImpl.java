package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.OrderItemMapper;
import com.hejie.tmall_ssm.pojo.*;
import com.hejie.tmall_ssm.service.OrderItemService;
import com.hejie.tmall_ssm.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Program: tmall_ssm
 * @Description: OrderItem接口实现类
 * @Author: hejie
 * @Create: 2019-07-18 11:15
 */
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemMapper orderItemMapper;

    @Autowired
    ProductService productService;

    @Override
    public void add(OrderItem orderItem) {
        orderItemMapper.insert(orderItem);
    }

    @Override
    public void delete(int id) {
        orderItemMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void update(OrderItem orderItem) {
        orderItemMapper.updateByPrimaryKeySelective(orderItem);
    }

    @Override
    public OrderItem get(int id) {
        return orderItemMapper.selectByPrimaryKey(id);
    }

    @Override
    public OrderItemExpand getE(int id) {
        OrderItemExpand orderItemExpand = new OrderItemExpand();
        orderItemExpand.setOrderItem(orderItemMapper.selectByPrimaryKey(id));
        setProduct(orderItemExpand);
        return orderItemExpand;
    }

    @Override
    public List<OrderItem> list() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id asc");
        return orderItemMapper.selectByExample(orderItemExample);
    }

    @Override
    public List<OrderItemExpand> listE() {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.setOrderByClause("id asc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        return getOrderItemExpandsByOrderItems(orderItems);
    }

    @Override
    public void fill(List<OrderExpand> orderExpands) {
        for (OrderExpand orderExpand : orderExpands) {
            fill(orderExpand);
        }
    }

    @Override
    public void fill(OrderExpand orderExpand) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andOidEqualTo(orderExpand.getId());
        orderItemExample.setOrderByClause("id asc");
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);
        List<OrderItemExpand> orderItemExpands = getOrderItemExpandsByOrderItems(orderItems);

        float total = 0;
        int totalNumber = 0;

        for (OrderItemExpand orderItemExpand : orderItemExpands) {
            total += orderItemExpand.getNumber() * orderItemExpand.getProduct().getPromote_price();
            totalNumber += orderItemExpand.getNumber();
        }

        orderExpand.setTotal(total);
        orderExpand.setTotalNumber(totalNumber);
        orderExpand.setOrderItems(orderItems);
        orderExpand.setOrderItemExpands(orderItemExpands);
    }

    /**
     *  设置订单项关联的产品
     */
    public void setProduct(List<OrderItemExpand> orderItemExpands) {
        for (OrderItemExpand orderItemExpand : orderItemExpands) {
            setProduct(orderItemExpand);
        }
    }

    private void setProduct(OrderItemExpand orderItemExpand) {
        Product product = productService.get(orderItemExpand.getPid());
        orderItemExpand.setProduct(product);
        ProductExpand productExpand = productService.getPe(orderItemExpand.getPid());
        orderItemExpand.setProductExpand(productExpand);
    }

    @Override
    public int getSales(int pid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andPidEqualTo(pid);
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        int count = 0;

        for (OrderItem orderItem : orderItems) {
            count += orderItem.getNumber();
        }

        return count;
    }

    @Override
    public List<OrderItem> listByU(int uid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid).andOidIsNull();

        return orderItemMapper.selectByExample(orderItemExample);
    }

    @Override
    public List<OrderItemExpand> listEByU(int uid) {
        OrderItemExample orderItemExample = new OrderItemExample();
        orderItemExample.createCriteria().andUidEqualTo(uid).andOidIsNull();
        List<OrderItem> orderItems = orderItemMapper.selectByExample(orderItemExample);

        return getOrderItemExpandsByOrderItems(orderItems);
    }

    /**
     *  根据订单项列表获取订单项拓展列表
     */
    public List<OrderItemExpand> getOrderItemExpandsByOrderItems(List<OrderItem> orderItems) {
        List<OrderItemExpand> orderItemExpands = new ArrayList<>();
        OrderItemExpand orderItemExpand;

        for (OrderItem orderItem : orderItems) {
            orderItemExpand = new OrderItemExpand();
            orderItemExpand.setOrderItem(orderItem);
            setProduct(orderItemExpand);
            orderItemExpands.add(orderItemExpand);
        }

        return orderItemExpands;
    }

}
