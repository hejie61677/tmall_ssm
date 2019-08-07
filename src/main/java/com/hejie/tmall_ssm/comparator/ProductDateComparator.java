package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @Program: tmall_ssm
 * @Description: 新品比较器（将创建日期晚的排前面）
 * @Author: hejie
 * @Create: 2019-08-01 17:14
 */
public class ProductDateComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return product2.getCreate_date().compareTo(product1.getCreate_date());
    }
}
