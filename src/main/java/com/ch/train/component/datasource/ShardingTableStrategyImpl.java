//package com.ch.train.component.datasource;
//
//import com.ch.train.form.DataAuthForm;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.Map;
//
///**
// * @author DXM-0189
// */
//@Component
//public class ShardingTableStrategyImpl implements ShardingTableStrategy {
//
//
//    /**
//     * 返回物流表名，根据用户id 和 表类型
//     * @param dataAuthForm
//     * @param dataBaseTableType
//     * @return
//     */
//    @Override
//    public String getDataBaseType(DataAuthForm dataAuthForm,DataBaseTableType dataBaseTableType) {
//        Integer userId = dataAuthForm.getUserId();
//        int i = userId % 2;
//        return dataBaseTableType.getTableName()+"_"+i;
//    }
//}
