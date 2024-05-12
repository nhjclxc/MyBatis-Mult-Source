package com.nhjclxc.mms.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解实现事务
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomTransactional {

    /**
     * 回滚的是哪个数据源
     */
    String rollBackSource();

    String SOURCE_1 = "source1";
    String SOURCE_2 = "source2";


}

