package com.ch.train.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author DXM-0189
 */
public class CollectProduct {


    /**
     * 商品名称
     */
    private String title;

    /**
     * 商品价格
     */
    private String price;

    /**
     * 商品描述
     */
    private String desc;

    /**
     * 商品图片
     */
    private List<String> images;


    /**
     * 商品变种信息
     */
    private String variations;

    public String getTitle() {
        return title;
    }

    public CollectProduct setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getPrice() {
        return price;
    }

    public CollectProduct setPrice(String price) {
        this.price = price;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public CollectProduct setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public List<String> getImages() {
        return images;
    }

    public CollectProduct setImages(List<String> images) {
        this.images = images;
        return this;
    }

    public String getVariations() {
        return variations;
    }

    public CollectProduct setVariations(String variations) {
        this.variations = variations;
        return this;
    }
}
