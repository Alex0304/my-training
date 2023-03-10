package com.ch.train.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author DXM-0189
 */
public class ProductQueryConditionBuilder implements ConditionBuilder{

    private StringBuilder conditionBuilder;

    private List<Object> objects;

    public ProductQueryConditionBuilder(StringBuilder conditionBuilder, List<Object> objects) {
        this.conditionBuilder = conditionBuilder;
        this.objects = objects;
    }

    public static ProductQueryConditionBuilder builder(String baseSql){
        return new ProductQueryConditionBuilder(new StringBuilder(baseSql),new ArrayList<>());
    }

    public ProductQueryConditionBuilder andIdEquals(Integer id){
        if(Objects.isNull(id)){
            return this;
        }
        conditionBuilder.append(" id = ?");
        objects.add(id);
        return this;
    }


    public ProductQueryConditionBuilder andNameEquals(String name){
        if(StringUtils.isEmpty(name)){
            return this;
        }
        conditionBuilder.append(" and name = ?");
        objects.add(name);
        return this;
    }

    public ProductQueryConditionBuilder userIdEquals(Integer userId){
        if(Objects.isNull(userId)){
            return this;
        }
        conditionBuilder.append(" user_id = ?");
        objects.add(userId);
        return this;
    }

    public ProductQueryConditionBuilder orderByUpdateTimeDesc(){
        conditionBuilder.append(" order by update_time desc");
        return this;
    }

    public ProductQueryConditionBuilder limit(int page, int size){
        conditionBuilder.append(" limit ?,?");
        if(page<=1){
            objects.add(0);
        }else {
            objects.add((page -1)*size);
        }
        objects.add(size);
        return this;
    }

    public ProductQueryConditionBuilder where(){
        conditionBuilder.append(" where ");
        return this;
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
