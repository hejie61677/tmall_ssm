package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @Program: tmall_ssm
 * @Description: 价格比较器（将价格低的放在前面）
 * @Author: hejie
 * @Create: 2019-08-01 17:39
 */
public class ProductPriceComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return (int) (product1.getPromote_price() - product2.getPromote_price());
    }
}
