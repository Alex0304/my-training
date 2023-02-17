package com.ch.train.component.datasource;

import java.lang.annotation.*;

/**
 * @author DXM-0189
 * 分表注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ShardingTable {

    String tableName();
}
