package com.ch.train.component.datasource;

import com.ch.train.form.DataAuthForm;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author DXM-0189
 * 动态数据源切面
 */
@Aspect
@Component
public class DynamicDatasourceAspect {

    @Resource
    private ShardingDatabaseStrategy shardingDatabaseStrategy;

    /**
     * 凡是注解了这个方法的，都需要做数据源切换
     * 数据源的切换策略，根据用户id来选择
     * 用户id在controller 进行赋值操作，
     * 从请求参数中获取用户id，没有进行赋值，则选择默认的主数据源
     * @param point
     * @param dataSource
     */
    @Before("@annotation(dataSource)")
    public void changeDataSource(JoinPoint point, SharedDataSource dataSource) {
        Object[] args = point.getArgs();
        if(Objects.isNull(args)){
            return;
        }
        Object o = Arrays.stream(Arrays.stream(args).toArray()).filter(e -> e instanceof DataAuthForm).findFirst().orElse(null);
        if(Objects.isNull(o)){
            return;
        }
        DataAuthForm dataAuthForm = (DataAuthForm) o;
        if(Objects.isNull(dataAuthForm.getUserId())){
            return;
        }
        //按用户ID首位奇偶分成两个库
        DataBaseType dataBaseType = shardingDatabaseStrategy.getShardDataBaseType(dataAuthForm);
        DataSourceContextHolder.setDataBaseType(dataBaseType);
    }

    @After("@annotation(dataSource)")
    public void restoreDataSource(JoinPoint point, SharedDataSource dataSource) {
        // 注意清除，不影响其他使用默认数据源master
        DataSourceContextHolder.cleanDataBaseType();
    }

}
