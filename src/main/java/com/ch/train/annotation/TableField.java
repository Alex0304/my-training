package com.ch.train.annotation;


import java.lang.annotation.*;

/**
 * @author DXM-0189
 * mysql 表字段与entity 实体类的映射标记
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TableField {

    String value();
}
