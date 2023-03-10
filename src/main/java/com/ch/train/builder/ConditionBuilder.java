package com.ch.train.builder;

import java.util.List;

/**
 * @author DXM-0189
 * 构造查询条件
 */
public interface ConditionBuilder {

    String getSql();

    Object[] getArgs();
}
