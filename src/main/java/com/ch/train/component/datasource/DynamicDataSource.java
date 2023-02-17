package com.ch.train.component.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Optional;

/**
 * @author DXM-0189
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return Optional.ofNullable(DataSourceContextHolder.getDataBaseType()).orElse(DataBaseType.MASTER).name();
    }
}
