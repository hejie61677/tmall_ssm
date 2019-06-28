package com.hejie.tmall_ssm.pojo;

import java.util.Date;

public class Product {
    private Integer id;

    private String name;

    private String sub_title;

    private Float original_price;

    private Float promote_price;

    private Integer stock;

    private Integer cid;

    private Date create_date;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSub_title() {
        return sub_title;
    }

    public void setSub_title(String sub_title) {
        this.sub_title = sub_title == null ? null : sub_title.trim();
    }

    public Float getOriginal_price() {
        return original_price;
    }

    public void setOriginal_price(Float original_price) {
        this.original_price = original_price;
    }

    public Float getPromote_price() {
        return promote_price;
    }

    public void setPromote_price(Float promote_price) {
        this.promote_price = promote_price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Date getCreate_date() {
        return create_date;
    }

    public void setCreate_date(Date create_date) {
        this.create_date = create_date;
    }
}