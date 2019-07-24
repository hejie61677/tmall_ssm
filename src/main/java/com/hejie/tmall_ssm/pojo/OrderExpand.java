package com.hejie.tmall_ssm.pojo;

import com.hejie.tmall_ssm.service.OrderService;

import java.util.List;

/**
 * @program: tmall_ssm
 * @description: Order拓展类
 * @author: hejie
 * @create: 2019-07-18 10:42
 */
public class OrderExpand extends Order {

    private List<OrderItem> orderItems;

    private List<OrderItemExpand> orderItemExpands;

    private User user;

    private float total;

    private int totalNumber;


    public List<OrderItem> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
    }

    public void setOrder(Order order) {
        this.setAddress(order.getAddress());
        this.setConfirm_date(order.getConfirm_date());
        this.setCreate_date(order.getCreate_date());
        this.setDelivery_date(order.getDelivery_date());
        this.setId(order.getId());
        this.setMobile(order.getMobile());
        this.setOrder_code(order.getOrder_code());
        this.setPay_date(order.getPay_date());
        this.setPost(order.getPost());
        this.setReceiver(order.getReceiver());
        this.setStatus(order.getStatus());
        this.setUid(order.getUid());
        this.setUser_message(order.getUser_message());
    }

    public String getStatusDesc() {
        String desc = "未知";
        switch (getStatus()) {
            case OrderService.waitPay:
                desc="待付款";
                break;
            case OrderService.waitDelivery:
                desc="待发货";
                break;
            case OrderService.waitConfirm:
                desc="待收货";
                break;
            case OrderService.waitReview:
                desc="等评价";
                break;
            case OrderService.finish:
                desc="完成";
                break;
            case OrderService.delete:
                desc="刪除";
                break;
            default:
                desc="未知";
        }
        return desc;
    }

    public List<OrderItemExpand> getOrderItemExpands() {
        return orderItemExpands;
    }

    public void setOrderItemExpands(List<OrderItemExpand> orderItemExpands) {
        this.orderItemExpands = orderItemExpands;
    }
}