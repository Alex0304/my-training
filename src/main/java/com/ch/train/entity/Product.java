package com.ch.train.entity;

import com.ch.train.component.datasource.ShardingTable;

import java.math.BigDecimal;
import java.util.Date;
import java.io.Serializable;

/**
 * 商品表(Product)实体类
 *
 * @author chenhuan
 * @since 2023-02-15 10:41:40
 */
@ShardingTable(tableName = "product")
public class Product implements Serializable {
    private static final long serialVersionUID = -93465391148724010L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 商品名称
     */
    private String name;
    /**
     * 商品描述
     */
    private String desc;
    /**
     * 商品价格
     */
    private BigDecimal price;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;


    /**
     * 用户id
     */
    private Integer userId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public Product setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }
}

