package com.ch.train.component.factory.sql.impl;

import com.ch.train.component.factory.sql.SqlGenerator;

/**
 * @author DXM-0189
 * 产品模块sql 生成器
 */
public enum ProductSqlGenerator implements SqlGenerator {

    QUERY_ONE("select * from product where id=?", "product", "查询单个商品信息"),
    QUERY_PAGE("select id,name,`desc`,price,create_time,update_time,user_id from product", "product", "查询商品分页"),
    QUERY_COUNT("select count(1) from product", "product", "查询商品数量统计"),
    INSERT_ONE("insert into product(name,`desc`,price,user_id,create_time,update_time) values (?,?,?,?,?,?);", "product", "插入一条商品信息"),
    UPDATE_ONE("update product set name = ?,`desc` =?,price =?,update_time = ? where id =?;", "product", "更新一条商品信息"),
    DELETE_ONE("delete from product  where id = ? and user_id = ?;", "product", "删除一条商品信息");


    /**
     * sql 语句
     */
    private String sql;


    /**
     * 表名
     */
    private String table;

    /**
     * sql 描述
     */
    private String desc;

    ProductSqlGenerator(String sql, String table, String desc) {
        this.sql = sql;
        this.table = table;
        this.desc = desc;
    }

    @Override
    public String getSql() {
        return sql;
    }

    public ProductSqlGenerator setSql(String sql) {
        this.sql = sql;
        return this;
    }

    @Override
    public String getTable() {
        return table;
    }

    public ProductSqlGenerator setTable(String table) {
        this.table = table;
        return this;
    }

    public String getDesc() {
        return desc;
    }

    public ProductSqlGenerator setDesc(String desc) {
        this.desc = desc;
        return this;
    }
}
