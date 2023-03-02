package com.ch.train.component.factory.sql;

/**
 * @author DXM-0189
 */
public interface SqlGenerator {

    /**
     * 获取sql
     * @return
     */
    String getSql();

    /**
     *
     */
    String getTable();
}
