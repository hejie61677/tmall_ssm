package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @Program: tmall_ssm
 * @Description: 销量比较器（将销量高的排在前面）
 * @Author: hejie
 * @Create: 2019-08-01 17:36
 */
public class ProductSalesComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return product2.getSales() - product1.getSales();
    }
}
