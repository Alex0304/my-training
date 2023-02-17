package com.ch.train.form;

import com.ch.train.entity.Product;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author DXM-0189
 */
public class ProductSaveForm extends DataAuthForm{

    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品描述
     */
    private String desc;
    /**
     * 商品价格
     */
    private BigDecimal price;

    public Integer getId() {
        return id;
    }

    public ProductSaveForm setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public ProductSaveForm setName(String name) {
        this.name = name;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ProductSaveForm setDesc(String desc) {
        this.desc = desc;
        return this;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public ProductSaveForm setPrice(BigDecimal price) {
        this.price = price;
        return this;
    }
}
