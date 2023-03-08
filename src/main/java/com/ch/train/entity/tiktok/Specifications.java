package com.ch.train.entity.tiktok;

/**
 * @author DXM-0189
 * 产品规格属性
 */
public class Specifications {


    private String name;
    private String value;

    public String getName() {
        return name;
    }

    public Specifications setName(String name) {
        this.name = name;
        return this;
    }

    public String getValue() {
        return value;
    }

    public Specifications setValue(String value) {
        this.value = value;
        return this;
    }
}
