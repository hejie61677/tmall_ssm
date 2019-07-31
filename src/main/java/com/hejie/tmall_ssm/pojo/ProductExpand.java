package com.hejie.tmall_ssm.pojo;


import java.util.List;

/**
 * @program: tmall_ssm
 * @description: Product类的拓展类
 * @author: hejie
 * @create: 2019-06-28
 */
public class ProductExpand extends Product {

    //产品所属分类信息
    private Category category;

    //产品主图片
    private ProductImage firstProductImage;

    //单个产品图片集合
    private List<ProductImage> productSingleImages;

    //详情产品图片集合
    private List<ProductImage> productDetailImages;

    //销量
    private int sales;

    //累计评价
    private int reviews;

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

    public List<ProductImage> getProductSingleImages() {
        return productSingleImages;
    }

    public void setProductSingleImages(List<ProductImage> productSingleImages) {
        this.productSingleImages = productSingleImages;
    }

    public List<ProductImage> getProductDetailImages() {
        return productDetailImages;
    }

    public void setProductDetailImages(List<ProductImage> productDetailImages) {
        this.productDetailImages = productDetailImages;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
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
