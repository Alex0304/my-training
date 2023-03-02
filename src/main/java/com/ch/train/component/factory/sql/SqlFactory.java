package com.ch.train.component.factory.sql;

import com.ch.train.form.DataAuthForm;

/**
 * @author DXM-0189
 * sql 生成工厂
 */
public interface SqlFactory {

    /**
     * 根据sql 生成器生成sql
     * @param sqlGenerator
     * @return
     */
    String getBaseSql(SqlGenerator sqlGenerator, DataAuthForm dataAuthForm);
}
