package com.ch.train.component.factory.sql;

import com.ch.train.form.DataAuthForm;

/**
 * @author DXM-0189
 * 查询时拼接sql 查询参数
 */
public interface SqlAppender<T extends DataAuthForm> {

    /**
     * 拼接原始sql
     * @param sql
     * @return
     */
    String append(String sql,T t);
}
