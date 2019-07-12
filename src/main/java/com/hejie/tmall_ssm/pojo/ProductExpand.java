package com.hejie.tmall_ssm.pojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: tmall_ssm
 * @description: Product类的拓展类
 * @author: hejie
 * @create: 2019-06-28
 */
public class ProductExpand extends Product {

    private Category category;

    private ProductImage firstProductImage;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public ProductImage getFirstProductImage() {
        return firstProductImage;
    }

    public void setFirstProductImage(ProductImage firstProductImage) {
        this.firstProductImage = firstProductImage;
    }

    public void setProduct(Product product) {
        this.setSub_title(product.getSub_title());
        this.setStock(product.getStock());
        this.setPromote_price(product.getPromote_price());
        this.setOriginal_price(product.getOriginal_price());
        this.setCreate_date(product.getCreate_date());
        this.setCid(product.getCid());
        this.setId(product.getId());
        this.setName(product.getName());
    }

}
