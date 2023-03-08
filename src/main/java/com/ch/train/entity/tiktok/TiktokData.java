package com.ch.train.entity.tiktok;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author DXM-0189
 */
public class TiktokData {

    @JsonAlias("2")
    private ProductDetail productDetail;

    public ProductDetail getProductDetail() {
        return productDetail;
    }

    public TiktokData setProductDetail(ProductDetail productDetail) {
        this.productDetail = productDetail;
        return this;
    }
}
