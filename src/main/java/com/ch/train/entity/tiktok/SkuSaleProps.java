package com.ch.train.entity.tiktok;

/**
 * @author DXM-0189
 * sku 销售属性
 */
public class SkuSaleProps {
    private String prop_id;
    private String prop_name;
    private String prop_value_id;
    private String prop_value;

    public String getProp_id() {
        return prop_id;
    }

    public SkuSaleProps setProp_id(String prop_id) {
        this.prop_id = prop_id;
        return this;
    }

    public String getProp_name() {
        return prop_name;
    }

    public SkuSaleProps setProp_name(String prop_name) {
        this.prop_name = prop_name;
        return this;
    }

    public String getProp_value_id() {
        return prop_value_id;
    }

    public SkuSaleProps setProp_value_id(String prop_value_id) {
        this.prop_value_id = prop_value_id;
        return this;
    }

    public String getProp_value() {
        return prop_value;
    }

    public SkuSaleProps setProp_value(String prop_value) {
        this.prop_value = prop_value;
        return this;
    }
}
