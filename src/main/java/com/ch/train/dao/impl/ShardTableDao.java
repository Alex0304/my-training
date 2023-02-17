package com.ch.train.dao.impl;

import com.ch.train.form.DataAuthForm;

/**
 * @author DXM-0189
 */
public interface ShardTableDao<T> {

     String replaceSql(String sql, Class<T> t, DataAuthForm form);
}
