package com.hejie.tmall_ssm.comparator;

import com.hejie.tmall_ssm.pojo.ProductExpand;

import java.util.Comparator;

/**
 * @program: tmall_ssm
 * @description: 人气比较器（将评论数量高的排前面）
 * @author: hejie
 * @create: 2019-08-01 17:13
 */
public class ProductReviewComparator implements Comparator<ProductExpand> {

    @Override
    public int compare(ProductExpand product1, ProductExpand product2) {
        return product2.getReviews() - product1.getReviews();
    }
}