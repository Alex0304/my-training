package com.ch.train.component.datasource;

/**
 * @author DXM-0189
 * 主从库类型
 */
public enum DataBaseType {

    MASTER("product"),
    SLAVE("product_copy");

    private String database;

    DataBaseType(String database) {
        this.database = database;
    }


    public String getDatabase() {
        return database;
    }

    public DataBaseType setDatabase(String database) {
        this.database = database;
        return this;
    }
}
