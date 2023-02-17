package com.ch.train.component.datasource;

import com.ch.train.form.DataAuthForm;
import org.springframework.stereotype.Component;

/**
 * @author DXM-0189
 */
@Component
public class ShardingDatabaseStrategyImpl implements ShardingDatabaseStrategy {


    @Override
    public DataBaseType getShardDataBaseType(DataAuthForm dataAuthForm) {
        Integer userId = dataAuthForm.getUserId();
        byte aByte = userId.toString().getBytes()[0];
        int numericValue = Character.getNumericValue(aByte);
        if(numericValue % 2 == 0){
            return DataBaseType.SLAVE;
        }
        return DataBaseType.MASTER;
    }

    @Override
    public int getShardTableType(DataAuthForm dataAuthForm) {
        Integer userId = dataAuthForm.getUserId();
        int i = userId % 2;
        return i;
    }

    /**
     * 获取分库分表
     * @param dataAuthForm
     * @return
     */
    @Override
    public String getShardDatabaseAndTable(DataAuthForm dataAuthForm) {
        DataBaseType shardDataBaseType = getShardDataBaseType(dataAuthForm);
        int i = getShardTableType(dataAuthForm);
        return shardDataBaseType.getDatabase()+":"+i;
    }
}
