package com.ch.train.form;

import java.io.Serializable;


/**
 * @author DXM-0189
 * 数据权限设置表单
 */
public class DataAuthForm implements Serializable {

    public DataAuthForm() {
    }

    public DataAuthForm(Integer userId) {
        this.userId = userId;
    }

    private Integer userId;

    public Integer getUserId() {
        return userId;
    }

    public DataAuthForm setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
}
