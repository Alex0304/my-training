package com.ch.train.component.factory.sql.impl;

import com.ch.train.component.factory.sql.SqlFactory;
import com.ch.train.component.factory.sql.SqlGenerator;
import com.ch.train.form.DataAuthForm;

/**
 * @author DXM-0189
 * 抽象的sql 生成工厂
 */
public abstract class AbstractSqlFactory implements SqlFactory {

    /**
     * 1.对sql 进行预处理，修改sql 基础信息进行修改，修改表名，设置数据权限参数拼接
     * 2.对原始sql 进行后置处理，根据不同的场景 进行sql 拼接
     * @param sqlGenerator
     * @return
     */
    @Override
    public String getBaseSql(SqlGenerator sqlGenerator, DataAuthForm dataAuthForm) {
        String sql = prepareSql(sqlGenerator,dataAuthForm);
        postSql(sql,dataAuthForm);
        return sql;
    }

    /**
     * 待子类重写，给sql 进行参数封装，参数绑定
     * @param sql
     * @param dataAuthForm
     */
    protected  void postSql(String sql, DataAuthForm dataAuthForm){

    }

    abstract String prepareSql(SqlGenerator sqlGenerator, DataAuthForm dataAuthForm);


}
