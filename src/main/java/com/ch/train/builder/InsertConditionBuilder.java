package com.ch.train.builder;

import com.ch.train.annotation.TableField;
import com.ch.train.component.datasource.ShardingTable;
import com.ch.train.entity.Product;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author DXM-0189
 * 插入产品信息sql 构造器
 */
public class InsertConditionBuilder<T> implements ConditionBuilder{

    private T t;

    private StringBuilder conditionBuilder;

    private List<Object> objects;

    private InsertConditionBuilder(T t,StringBuilder conditionBuilder) throws IllegalAccessException {
        this.t = t;
        this.objects = new ArrayList<>();
        this.conditionBuilder = conditionBuilder;
        init();
    }

    public static <T> InsertConditionBuilder builder(String sql,T t) throws IllegalAccessException {
        InsertConditionBuilder insertConditionBuilder = new InsertConditionBuilder(t,new StringBuilder(sql));
        return insertConditionBuilder;
    }


    /**
     * 初始化
     * 根据product 实体对象生成sql 和参数对象
     * field_name = ?
     * args.add()
     */
    private void init() throws IllegalAccessException {
        Class<?> aClass = t.getClass();
        conditionBuilder.append("(");
        Field[] declaredFields = aClass.getDeclaredFields();
        List<Field> fieldList = Arrays.stream(declaredFields).filter(e -> Objects.nonNull(e.getDeclaredAnnotation(TableField.class))).collect(Collectors.toList());
        StringBuilder fieldValues = new StringBuilder("values(");
        for (Field field : fieldList) {
            field.setAccessible(true);
            Object fieldVal = field.get(t);
            if(Objects.isNull(fieldVal)){
                continue;
            }
            TableField annotation = field.getAnnotation(TableField.class);
            conditionBuilder.append(annotation.value()).append(",");
            fieldValues.append("?").append(",");
            objects.add(fieldVal);
        }
        removeLastChar(conditionBuilder).append(")").append(removeLastChar(fieldValues)).append(")");
    }

    private StringBuilder removeLastChar(StringBuilder originalStr){
        return originalStr.deleteCharAt(originalStr.lastIndexOf(","));
    }

    public static void main(String[] args) throws IllegalAccessException {
        Product product = new Product();
        product.setUserId(222);
        product.setDesc("jack");
        product.setName("dsdsd");
        product.setCreateTime(new Date());
        product.setUpdateTime(new Date());
        product.setPrice(new BigDecimal(22));
//        InsertConditionBuilder builder = InsertConditionBuilder.builder(product);
//        StringBuilder conditionBuilder1 = builder.conditionBuilder;
//        System.out.println(conditionBuilder1);
//        List objects1 = builder.objects;
//        System.out.println(objects1.size());

//        StringBuilder stringBuilder = new StringBuilder("223232323292");
//        System.out.println(stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("2")));
//        StringJoiner mystring = new StringJoiner("-");
//        // Joining multiple strings by using add() method
//         mystring.add("Logan");
//         mystring.add("Magneto");
//         mystring.add("Rogue");
//         mystring.add("Storm");
//         System.out.println(mystring);


    }

    @Override
    public String getSql() {
        return conditionBuilder.toString();
    }

    @Override
    public Object[] getArgs() {
        return objects.toArray();
    }
}
