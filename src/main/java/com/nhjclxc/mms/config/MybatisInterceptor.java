package com.nhjclxc.mms.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.util.Properties;

/**
 * Mybatis通用拦截器
 */
@Intercepts({
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})
})
@Slf4j
@Component
public class MybatisInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = null;
        if (invocation.getArgs().length > 1) {
            parameter = invocation.getArgs()[1];
        }

        BoundSql boundSql = mappedStatement.getBoundSql(parameter);
        Configuration configuration = mappedStatement.getConfiguration();

        long startTime = System.currentTimeMillis();
        Object returnVal = invocation.proceed();
        long costTime = System.currentTimeMillis() - startTime;

        // 排除保存sql执行日志的表 sys_sql_execute_record
        if (mappedStatement.getId().contains("sys_sql_execute_record")) {
            return returnVal;
        }
        try {
            String sql = boundSql.getSql();
            CustomThreadPoolExecutor.pool.execute(() -> saveSqlExcuteLog(sql, costTime, mappedStatement.getId()));
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        return returnVal;
    }

    /**
     * 保存sql执行日志
     */
    private void saveSqlExcuteLog(String sql, long costTime, String sqlName) {
        log.info("保存sql执行日志. {}, {}, {}", sql, costTime, sqlName);
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) {
    }


}
