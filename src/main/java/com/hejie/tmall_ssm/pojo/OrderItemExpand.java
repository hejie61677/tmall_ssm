package com.hejie.tmall_ssm.pojo;

/**
 * @program: tmall_ssm
 * @description: OrderItem拓展类
 * @author: hejie
 * @create: 2019-07-18 10:34
 */
public class OrderItemExpand extends OrderItem {

    //产品
    private Product product;

    //产品拓展
    private ProductExpand productExpand;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public void setOrderItem(OrderItem orderItem) {

        this.setId(orderItem.getId());
        this.setNumber(orderItem.getNumber());
        this.setOid(orderItem.getOid());
        this.setPid(orderItem.getPid());
        this.setUid(orderItem.getUid());
    }

    public ProductExpand getProductExpand() {
        return productExpand;
    }

    public void setProductExpand(ProductExpand productExpand) {
        this.productExpand = productExpand;
    }
}
