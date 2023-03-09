package com.ch.train.mapper;

import java.sql.ResultSet;
import java.util.List;

/**
 * @author DXM-0189
 */
public interface MyObjectMapper {

    /**
     * 根据mysql 查询回的数据进行转换
     * @param resultSet
     * @return
     */
    <T> List<T> convertList(ResultSet resultSet);


    /**
     * 查询返回单个对象
     * @param resultSet
     * @return
     */
    <T> T convertOne(ResultSet resultSet);




}
