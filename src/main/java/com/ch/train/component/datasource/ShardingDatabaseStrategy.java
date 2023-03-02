package com.ch.train.component.datasource;

import com.ch.train.form.DataAuthForm;

/**
 * @author DXM-0189
 * 分库策略
 */
public interface ShardingDatabaseStrategy {

    /**
     * 根据数据权限表单获取分库策略
     * @param dataAuthForm
     * @return
     */
    DataBaseType getShardDataBaseType(DataAuthForm dataAuthForm);



    /**
     * 根据数据权限表单获取分表策略
     * 根据用户id 来计算分表规则
     * 根据逻辑表来计算物理表
     * @param dataAuthForm
     * @return string
     */
    Integer getShardTableType(DataAuthForm dataAuthForm);


    /**
     * 计算分库分表
     * @param dataAuthForm
     * @return
     */
    String getShardDatabaseAndTable(DataAuthForm dataAuthForm);
}
