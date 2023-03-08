package com.ch.train.entity.tiktok;

import java.util.List;

/**
 * @author DXM-0189
 * 销售属性
 */
public class SaleProps {

    private String prop_id;
    private String prop_name;
    private boolean has_image;
    private List<SalePropValues> sale_prop_values;

    public String getProp_id() {
        return prop_id;
    }

    public SaleProps setProp_id(String prop_id) {
        this.prop_id = prop_id;
        return this;
    }

    public String getProp_name() {
        return prop_name;
    }

    public SaleProps setProp_name(String prop_name) {
        this.prop_name = prop_name;
        return this;
    }

    public boolean isHas_image() {
        return has_image;
    }

    public SaleProps setHas_image(boolean has_image) {
        this.has_image = has_image;
        return this;
    }

    public List<SalePropValues> getSale_prop_values() {
        return sale_prop_values;
    }

    public SaleProps setSale_prop_values(List<SalePropValues> sale_prop_values) {
        this.sale_prop_values = sale_prop_values;
        return this;
    }
}
