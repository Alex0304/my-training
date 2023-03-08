package com.ch.train.entity.tiktok;

import java.util.List;

/**
 * @author DXM-0189
 * 产品基础信息
 */
public class ProductBase {
    private String title;
    private String desc_detail;
    private List<Images> images;
    private List<Specifications> specifications;
    private int sold_count;
    private ProductPrice price;

    public String getTitle() {
        return title;
    }

    public ProductBase setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getDesc_detail() {
        return desc_detail;
    }

    public ProductBase setDesc_detail(String desc_detail) {
        this.desc_detail = desc_detail;
        return this;
    }

    public List<Images> getImages() {
        return images;
    }

    public ProductBase setImages(List<Images> images) {
        this.images = images;
        return this;
    }

    public List<Specifications> getSpecifications() {
        return specifications;
    }

    public ProductBase setSpecifications(List<Specifications> specifications) {
        this.specifications = specifications;
        return this;
    }

    public int getSold_count() {
        return sold_count;
    }

    public ProductBase setSold_count(int sold_count) {
        this.sold_count = sold_count;
        return this;
    }

    public ProductPrice getPrice() {
        return price;
    }

    public ProductBase setPrice(ProductPrice price) {
        this.price = price;
        return this;
    }
}
