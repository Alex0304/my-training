package com.ch.train.dao.impl;

/**
 * @author DXM-0189
 * 分表的dao
 */

import com.ch.train.component.datasource.DataBaseTableType;
import com.ch.train.component.datasource.ShardingDatabaseStrategy;
import com.ch.train.component.datasource.ShardingTable;
import com.ch.train.form.DataAuthForm;

import javax.annotation.Resource;


/**
 * 分表
 *
 * @param <T>
 * @author DXM-0189
 */
public abstract class AbstractShardTableDao<T> implements ShardTableDao<T> {

    @Resource
    private ShardingDatabaseStrategy shardingTableStrategy;

    /**
     * 从model注解中提取出逻辑表名，再根据分表策略，查询在哪张表，计算得到数据库的物理表名
     * 再将sql 中的逻辑表名替换为物理表名
     * 暂时只支持单表查询的替换
     * @param sql
     * @param tClass
     * @param dataAuthForm
     * @return
     */
    @Override
    public String replaceSql(String sql, Class<T> tClass, DataAuthForm dataAuthForm) {
        int shardTableType = shardingTableStrategy.getShardTableType(dataAuthForm);
        ShardingTable declaredAnnotation = tClass.getDeclaredAnnotation(ShardingTable.class);
        if(declaredAnnotation == null){
            return sql;
        }
        String logicTableName = declaredAnnotation.tableName();
        String actualTableName = logicTableName + "_" + shardTableType;
        return sql.replaceAll(logicTableName,actualTableName);
    }
}
