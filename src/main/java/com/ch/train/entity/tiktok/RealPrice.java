package com.ch.train.entity.tiktok;

/**
 * @author DXM-0189
 */
public class RealPrice {
    private String price_str;
    private String price_val;
    private String currency;

    public String getPrice_str() {
        return price_str;
    }

    public RealPrice setPrice_str(String price_str) {
        this.price_str = price_str;
        return this;
    }

    public String getPrice_val() {
        return price_val;
    }

    public RealPrice setPrice_val(String price_val) {
        this.price_val = price_val;
        return this;
    }

    public String getCurrency() {
        return currency;
    }

    public RealPrice setCurrency(String currency) {
        this.currency = currency;
        return this;
    }
}
