package com.ch.train.component.datasource;


import java.lang.annotation.*;

/**
 * @author DXM-0189
 * 数据源注解
 */
@Target({ElementType.METHOD, ElementType.TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SharedDataSource {

}
