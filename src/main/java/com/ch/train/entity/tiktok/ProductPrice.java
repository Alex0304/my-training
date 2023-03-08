package com.ch.train.entity.tiktok;

/**
 * @author DXM-0189
 * 产品价格
 */
public class ProductPrice {

    private String real_price;
    private String original_price;
    private String discount;
    private String original_price_value;

    public String getReal_price() {
        return real_price;
    }

    public ProductPrice setReal_price(String real_price) {
        this.real_price = real_price;
        return this;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public ProductPrice setOriginal_price(String original_price) {
        this.original_price = original_price;
        return this;
    }

    public String getDiscount() {
        return discount;
    }

    public ProductPrice setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getOriginal_price_value() {
        return original_price_value;
    }

    public ProductPrice setOriginal_price_value(String original_price_value) {
        this.original_price_value = original_price_value;
        return this;
    }
}
