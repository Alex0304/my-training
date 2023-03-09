package com.ch.train.mapper;

import com.ch.train.annotation.TableField;
import com.ch.train.entity.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DXM-0189
 */
public abstract class AbstractObjectMapper{


    /**
     * 1.要按照需要的属性值字段过滤出来
     * 2.根据哥哥属性字段的类型，从结果集中查询到对应的值
     * 3.将数据库的字段值反射赋值到对象属性上
     *
     * @param resultSet
     * @param tClass
     * @return
     */
    protected <T> List<T> extractData(ResultSet resultSet, Class<T> tClass) {
        List<T> dataList = null;
        try {
            Field[] declaredFields = tClass.getDeclaredFields();
            List<Field> filterFieldList = Arrays.asList(declaredFields).stream().filter(e -> Objects.nonNull(e.getAnnotation(TableField.class))).collect(Collectors.toList());
            dataList = new ArrayList<>();
            if(resultSet.getRow() == 1){
                dataList.add(extractSingleData(filterFieldList,resultSet,tClass));
            }
            while (resultSet.next()) {
                dataList.add(extractSingleData(filterFieldList,resultSet,tClass));
            }
        } catch (Exception e) {

        }
        return dataList;
    }

    protected <T> T extractSingleData(List<Field> fields,ResultSet resultSet,Class<T> tClass) throws InstantiationException, IllegalAccessException, SQLException {
        T t = tClass.newInstance();
        for (Field field : fields) {
            Object fieldVal = extractVal(field, resultSet);
            field.setAccessible(true);
            field.set(t, fieldVal);
        }
        return t;
    }

    private Object extractVal(Field field, ResultSet set) throws SQLException {
        TableField annotation = field.getAnnotation(TableField.class);
        String fieldName = annotation.value();
        Object val = null;
        Class<?> type = field.getType();
        if (type.isPrimitive()) {
            // 基本数据类型
            if (type.getName().equals("int")) {
                val = set.getInt(fieldName);
            }
        } else if (type.isAssignableFrom(Integer.class)) {
            val = set.getInt(fieldName);
        }else if (type.isAssignableFrom(BigDecimal.class)) {
            val = set.getBigDecimal(fieldName);
        }
        else if (type.isAssignableFrom(String.class)) {
            val = set.getString(fieldName);
        } else if (type.isAssignableFrom(Date.class)) {
            val = set.getTimestamp(fieldName);
        }
        return val;
    }

//    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
//        convert(Product.class);
//    }
//
//    public static <T> void convert(Class<T> tClass) throws InstantiationException, IllegalAccessException {
//        T t = tClass.newInstance();
//        Field[] declaredFields = tClass.getDeclaredFields();
//        List<Field> filterFieldList = Arrays.asList(declaredFields).stream().filter(e -> Objects.nonNull(e.getAnnotation(TableField.class))).collect(Collectors.toList());
//        for (Field field : filterFieldList) {
//            TableField annotation = field.getAnnotation(TableField.class);
//            String value = annotation.value();
//            Class<?> type = field.getType();
//            if (type.isPrimitive()) {
//                // 基本数据类型
//                System.out.println(field.getName() + ":" + type.getName());
//            } else if (type.isAssignableFrom(Integer.class)) {
//                System.out.println(field.getName() + ":" + type.getName());
//            } else if (type.isAssignableFrom(String.class)) {
//                System.out.println(field.getName() + ":" + type.getName());
//            } else if (type.isAssignableFrom(Date.class)) {
//                System.out.println(field.getName() + ":" + type.getName());
//            }
//        }
//    }
}
