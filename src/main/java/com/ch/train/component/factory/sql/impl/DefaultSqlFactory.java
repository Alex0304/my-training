package com.ch.train.component.factory.sql.impl;

import com.ch.train.component.factory.sql.SqlGenerator;
import com.ch.train.form.DataAuthForm;
import org.springframework.stereotype.Component;

/**
 * @author DXM-0189
 * 默认的sql 生成工厂
 */
@Component
public class DefaultSqlFactory extends AbstractSqlFactory {

    @Override
    String prepareSql(SqlGenerator sqlGenerator, DataAuthForm dataAuthForm) {
        return sqlGenerator.getSql();
    }
}
