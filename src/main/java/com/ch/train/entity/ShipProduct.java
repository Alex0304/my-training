package com.ch.train.entity;


import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.io.Serializable;

/**
 * @author DXM-0189
 */
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class ShipProduct implements Serializable {

    private String expressType;

    private String productDoornorule;
    private String productId;
    private String productShortname;
    private String productTracknoapitype;

    public String getExpressType() {
        return expressType;
    }

    public void setExpressType(String expressType) {
        this.expressType = expressType;
    }

    public String getProductDoornorule() {
        return productDoornorule;
    }

    public void setProductDoornorule(String productDoornorule) {
        this.productDoornorule = productDoornorule;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductShortname() {
        return productShortname;
    }

    public void setProductShortname(String productShortname) {
        this.productShortname = productShortname;
    }

    public String getProductTracknoapitype() {
        return productTracknoapitype;
    }

    public void setProductTracknoapitype(String productTracknoapitype) {
        this.productTracknoapitype = productTracknoapitype;
    }
}
