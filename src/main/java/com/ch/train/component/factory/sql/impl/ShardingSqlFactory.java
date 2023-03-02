package com.ch.train.component.factory.sql.impl;

import com.ch.train.component.datasource.ShardingDatabaseStrategy;
import com.ch.train.component.factory.sql.SqlGenerator;
import com.ch.train.form.DataAuthForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author DXM-0189
 * 分表的sql 工厂，需要实现
 */
@Component
public class ShardingSqlFactory extends AbstractSqlFactory {

    @Autowired
    private ShardingDatabaseStrategy shardingDatabaseStrategy;


    @Override
    String prepareSql(SqlGenerator sqlGenerator, DataAuthForm dataAuthForm) {
        Integer shardTableType = shardingDatabaseStrategy.getShardTableType(dataAuthForm);
        String originalSql = sqlGenerator.getSql();
        if(Objects.isNull(shardTableType)){
            return originalSql;
        }
        String logicTableName = sqlGenerator.getTable();
        String actualTableName = logicTableName + "_" + shardTableType;
        return originalSql.replaceAll(logicTableName,actualTableName);
    }
}
