package com.ch.train.entity.tiktok;

/**
 * @author DXM-0189
 * 销售属性值
 */
public class SalePropValues {


    private String prop_value_id;
    private String prop_value;
    private Image image;

    public String getProp_value_id() {
        return prop_value_id;
    }

    public SalePropValues setProp_value_id(String prop_value_id) {
        this.prop_value_id = prop_value_id;
        return this;
    }

    public String getProp_value() {
        return prop_value;
    }

    public SalePropValues setProp_value(String prop_value) {
        this.prop_value = prop_value;
        return this;
    }

    public Image getImage() {
        return image;
    }

    public SalePropValues setImage(Image image) {
        this.image = image;
        return this;
    }
}
