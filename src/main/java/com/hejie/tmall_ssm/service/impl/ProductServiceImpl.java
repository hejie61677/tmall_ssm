package com.hejie.tmall_ssm.service.impl;

import com.hejie.tmall_ssm.mapper.CategoryMapper;
import com.hejie.tmall_ssm.mapper.ProductMapper;
import com.hejie.tmall_ssm.pojo.*;
import com.hejie.tmall_ssm.service.OrderItemService;
import com.hejie.tmall_ssm.service.ProductImageService;
import com.hejie.tmall_ssm.service.ProductService;
import com.hejie.tmall_ssm.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
  * @Program: tmall_ssm
  * @Description: Product接口实现类
  * @Author: hejie
  * @Create: 2019/6/28
  */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryMapper categoryMapper;

    @Autowired
    ProductImageService productImageService;

    @Autowired
    OrderItemService orderItemService;

    @Autowired
    ReviewService reviewService;

    @Override
    public List<Product> list(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id asc");
        return productMapper.selectByExample(productExample);
    }

    @Override
    public List<ProductExpand> listPe(int cid) {
        ProductExample productExample = new ProductExample();
        productExample.createCriteria().andCidEqualTo(cid);
        productExample.setOrderByClause("id asc");
        List<Product> products = productMapper.selectByExample(productExample);
        List<ProductExpand> productExpands = new ArrayList<>();
        setProductExpands(productExpands, products);
        setFirstProductImage(productExpands);
        return productExpands;
    }

    @Override
    public void add(Product product) {
        productMapper.insert(product);
    }

    @Override
    public void delete(int id) {
        productMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Product get(int id) {
        return productMapper.selectByPrimaryKey(id);
    }

    @Override
    public ProductExpand getPe(int id) {
        Product product = productMapper.selectByPrimaryKey(id);
        ProductExpand productExpand = new ProductExpand();
        productExpand.setCategory(categoryMapper.selectByPrimaryKey(product.getCid()));
        productExpand.setProduct(product);
        setFirstProductImage(productExpand);
        return productExpand;
    }

    @Override
    public void update(Product product) {
        productMapper.updateByPrimaryKey(product);
    }

    @Override
    public void setFirstProductImage(ProductExpand productExpand) {
        List<ProductImage> productImages = productImageService.list(productExpand.getId(), ProductImageService.type_single);
        if (!productImages.isEmpty()) {
            ProductImage productImage = productImages.get(0);
            productExpand.setFirstProductImage(productImage);
        }
    }

    @Override
    public void fill(List<CategoryExpand> categoryExpands) {
        for (CategoryExpand categoryExpand : categoryExpands) {
            fill(categoryExpand);
        }
    }

    @Override
    public void fill(CategoryExpand categoryExpand) {
        List<ProductExpand> productExpands = listPe(categoryExpand.getId());
        categoryExpand.setProducts(productExpands);
    }

    @Override
    public void fillByRow(List<CategoryExpand> categoryExpands) {
        int productNumberEachRow = 8;

        for (CategoryExpand categoryExpand : categoryExpands) {
            List<ProductExpand> products = categoryExpand.getProducts();
            setFirstProductImage(products);
            List<List<ProductExpand>> productsByRow = new ArrayList<>();

            for (int i = 0; i < products.size(); i += productNumberEachRow) {
                int size = i + productNumberEachRow;
                size = size > products.size() ? products.size() : size;
                List<ProductExpand> productOfEachRow = products.subList(i, size);
                productsByRow.add(productOfEachRow);
            }

            categoryExpand.setProductsByRow(productsByRow);
        }
    }

    @Override
    public void setSaleAndReview(ProductExpand productExpand) {
        int sales = orderItemService.getSales(productExpand.getId());
        int reviews = reviewService.getCount(productExpand.getId());

        productExpand.setSales(sales);
        productExpand.setReviews(reviews);
    }

    @Override
    public void setSalesAndReviews(List<ProductExpand> productExpands) {
        for (ProductExpand productExpand : productExpands) {
            setSaleAndReview(productExpand);
        }
    }

    public void setFirstProductImage(List<ProductExpand> productExpands) {
        for (ProductExpand productExpand : productExpands) {
            setFirstProductImage(productExpand);
        }
    }

    public void setProductExpands(List<ProductExpand> productExpands, List<Product> products) {
        for (Product product : products) {
            ProductExpand productExpand = new ProductExpand();
            productExpand.setProduct(product);
            productExpands.add(productExpand);
        }
    }
}