package com.ch.train.dao.impl;

import com.ch.train.component.factory.sql.SqlFactory;
import com.ch.train.component.factory.sql.impl.ProductSqlGenerator;
import com.ch.train.form.DataAuthForm;

/**
 * @author DXM-0189
 */
public abstract class BaseDaoImpl {

    private SqlFactory sqlFactory;

    public BaseDaoImpl(SqlFactory sqlFactory) {
        this.sqlFactory = sqlFactory;
    }


    /**
     * 通过sql 生成器和用户信息对sql 进行表名的替换，如果使用了分表
     * @param productSqlGenerator
     * @param dataAuthForm
     * @return
     */
    protected String getBaseSql(ProductSqlGenerator productSqlGenerator, DataAuthForm dataAuthForm){
        return sqlFactory.getBaseSql(productSqlGenerator,dataAuthForm);
    }


}
