package com.ch.train.component.datasource;

/**
 * @author DXM-0189
 * 数据源holder
 */
public class DataSourceContextHolder {

    private static final ThreadLocal<DataBaseType> DATA_BASE_TYPE_THREAD_LOCAL = new ThreadLocal<>();


    /**
     * 获取数据源类型
     * @return
     */
    public static DataBaseType getDataBaseType(){
        return DATA_BASE_TYPE_THREAD_LOCAL.get();
    }


    /**
     * 设置数据源类型
     * @return
     */
    public static void setDataBaseType(DataBaseType dataBaseType){
        DATA_BASE_TYPE_THREAD_LOCAL.set(dataBaseType);
    }


    /**
     * 清理数据源类型
     * @return
     */
    public static void cleanDataBaseType(){
        DATA_BASE_TYPE_THREAD_LOCAL.remove();
    }
}
