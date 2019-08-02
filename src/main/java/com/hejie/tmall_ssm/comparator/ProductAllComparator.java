package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @program: tmall_ssm
 * @description: 综合比较器（将"销量×评价"高的排前面）
 * @author: hejie
 * @create: 2019-08-01 16:36
 */
public class ProductAllComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return product2.getReviews() * product2.getSales() - product1.getReviews() * product1.getSales();
    }
}
