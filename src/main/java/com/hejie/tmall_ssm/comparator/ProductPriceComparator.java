package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @program: tmall_ssm
 * @description: 价格比较器（将价格低的放在前面）
 * @author: hejie
 * @create: 2019-08-01 17:39
 */
public class ProductPriceComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return (int) (product1.getPromote_price() - product2.getPromote_price());
    }
}
