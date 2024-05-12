package com.nhjclxc.mms.annotation;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Component;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;

import javax.annotation.Resource;

/**
 * 通过拦截自定义注解实现事务
 */
@Slf4j
@Aspect
@Component
public class CustomTransactionAspect {

    @Qualifier(CustomTransactional.SOURCE_1)
    @Resource
    private DataSourceTransactionManager source1DataSourceTransactionManager;

    @Qualifier(CustomTransactional.SOURCE_2)
    @Resource
    private DataSourceTransactionManager source2DataSourceTransactionManager;

    private final static DefaultTransactionAttribute defaultTransactionAttribute = new DefaultTransactionAttribute();

    /**
     * 通过拦截自定义注解@CustomTransactional来实现事务 @annotation()里为自定义注解的路径
     **/
    @Around("@annotation(customTransactional)")
//    @Around("@annotation(com.nhjclxc.mms.annotation.CustomTransactional)")
    public Object around(ProceedingJoinPoint joinPoint, CustomTransactional customTransactional) {

        String type = customTransactional.rollBackSource();
        DataSourceTransactionManager dataSourceTransactionManager = CustomTransactional.SOURCE_1.equals(type) ?
                                                source1DataSourceTransactionManager: source2DataSourceTransactionManager;

        Object result = null;
        TransactionStatus transaction = null;
        try {
            // 开启事务
            transaction = dataSourceTransactionManager.getTransaction(defaultTransactionAttribute);

            // 执行目标方法
            result = joinPoint.proceed();

            // 提交事务
            dataSourceTransactionManager.commit(transaction);

        } catch (Throwable e) {
            if (transaction != null) {
                dataSourceTransactionManager.rollback(transaction);
            }
            log.info(type + " 事务回滚...");
            e.printStackTrace();
        }

        return result;
    }
}

