package com.ch.train.component.datasource;


/**
 * @author DXM-0189
 * 分表
 */
public enum DataBaseTableType {

    PRODUCT("product","商品表");

    private String tableName;

    private String tableDesc;

    DataBaseTableType(String tableName, String tableDesc) {
        this.tableName = tableName;
        this.tableDesc = tableDesc;
    }

    public String getTableName() {
        return tableName;
    }

    public DataBaseTableType setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public String getTableDesc() {
        return tableDesc;
    }

    public DataBaseTableType setTableDesc(String tableDesc) {
        this.tableDesc = tableDesc;
        return this;
    }
}
