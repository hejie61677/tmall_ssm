package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @Program: tmall_ssm
 * @Description: 综合比较器（将"销量×评价"高的排前面）
 * @Author: hejie
 * @Create: 2019-08-01 16:36
 */
public class ProductAllComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return product2.getReviews() * product2.getSales() - product1.getReviews() * product1.getSales();
    }
}
