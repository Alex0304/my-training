package com.ch.train.builder;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author DXM-0189
 */
public class ProductConditionBuilder implements ConditionBuilder{

    private StringBuilder conditionBuilder;

    private List<Object> objects;

    public ProductConditionBuilder(StringBuilder conditionBuilder, List<Object> objects) {
        this.conditionBuilder = conditionBuilder;
        this.objects = objects;
    }

    public static ProductConditionBuilder builder(String baseSql){
        return new ProductConditionBuilder(new StringBuilder(baseSql),new ArrayList<>());
    }

    public ProductConditionBuilder andIdEquals(Integer id){
        if(Objects.isNull(id)){
            return this;
        }
        conditionBuilder.append(" id = ?");
        objects.add(id);
        return this;
    }


    public ProductConditionBuilder andNameEquals(String name){
        if(StringUtils.isEmpty(name)){
            return this;
        }
        conditionBuilder.append(" and name = ?");
        objects.add(name);
        return this;
    }

    public ProductConditionBuilder userIdEquals(Integer userId){
        if(Objects.isNull(userId)){
            return this;
        }
        conditionBuilder.append(" user_id = ?");
        objects.add(userId);
        return this;
    }

    public ProductConditionBuilder orderByUpdateTimeDesc(){
        conditionBuilder.append(" order by update_time desc");
        return this;
    }

    public ProductConditionBuilder limit(int page,int size){
        conditionBuilder.append(" limit ?,?");
        if(page<=1){
            objects.add(0);
        }else {
            objects.add((page -1)*size);
        }
        objects.add(size);
        return this;
    }

    public ProductConditionBuilder where(){
        conditionBuilder.append(" where ");
        return this;
    }


    @Override
    public String getCondition() {
        return conditionBuilder.toString();
    }

    @Override
    public Object[] getArgs() {
        return objects.toArray();
    }

}
