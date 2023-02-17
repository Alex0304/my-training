package com.ch.train.form;

import org.springframework.data.domain.PageRequest;

import java.io.Serializable;

/**
 * @author DXM-0189
 */
public class ProductQueryPageForm extends DataAuthForm {

    private String name;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    private int page;

    private int size;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
