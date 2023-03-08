package com.ch.train.entity.tiktok;

/**
 * @author DXM-0189
 * 产品价格
 */
public class SkuPrice {

    private RealPrice real_price;
    private String original_price;
    private String discount;
    private String original_price_value;

    public RealPrice getReal_price() {
        return real_price;
    }

    public SkuPrice setReal_price(RealPrice real_price) {
        this.real_price = real_price;
        return this;
    }

    public String getOriginal_price() {
        return original_price;
    }

    public SkuPrice setOriginal_price(String original_price) {
        this.original_price = original_price;
        return this;
    }

    public String getDiscount() {
        return discount;
    }

    public SkuPrice setDiscount(String discount) {
        this.discount = discount;
        return this;
    }

    public String getOriginal_price_value() {
        return original_price_value;
    }

    public SkuPrice setOriginal_price_value(String original_price_value) {
        this.original_price_value = original_price_value;
        return this;
    }
}
