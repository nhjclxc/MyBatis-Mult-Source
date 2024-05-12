package com.nhjclxc.mms.config;


import com.nhjclxc.mms.annotation.CustomTransactional;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.ExecutorType;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.annotation.MapperScans;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

/**
 * MybatisConfig
 */
@Configuration
@EnableTransactionManagement(proxyTargetClass = true)
@EnableAspectJAutoProxy(exposeProxy = true)
@MapperScans({
        @MapperScan(basePackages = { "com.nhjclxc.mms.mapper.source1"}, sqlSessionFactoryRef = "source1SqlSessionFactory"),
        @MapperScan(basePackages = { "com.nhjclxc.mms.mapper.source2"}, sqlSessionFactoryRef = "source2SqlSessionFactory")
})
public class MybatisConfig implements EnvironmentAware {

    private Environment environment;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    /**
     * 以下是数据源1的配置
     */

    @Bean("source1DataSource")
    @Primary
    public DataSource source1DataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        hikariDataSource.setJdbcUrl(environment.getProperty("spring.datasource.source1.jdbc-url"));
        hikariDataSource.setUsername(environment.getProperty("spring.datasource.source1.username"));
        hikariDataSource.setPassword(environment.getProperty("spring.datasource.source1.password"));
        hikariDataSource.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.maximum-pool-size"))));
        hikariDataSource.setMinimumIdle(Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.minimum-idle"))));
        hikariDataSource.setConnectionTimeout(Long.parseLong(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.connection-timeout"))));
        hikariDataSource.setIdleTimeout(Long.parseLong(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.idle-timeout"))));
        hikariDataSource.setMaxLifetime(Long.parseLong(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.max-lifetime"))));
        hikariDataSource.setConnectionTestQuery(environment.getProperty("spring.datasource.hikari.connection-test-query"));
        hikariDataSource.setAutoCommit(Boolean.getBoolean(environment.getProperty("spring.datasource.hikari.auto-commit")));
        hikariDataSource.setPoolName("source1HikariCP");
        return hikariDataSource;
    }

    @Bean(value = CustomTransactional.SOURCE_1)
    @Primary
    public DataSourceTransactionManager transactionSource1Manager(@Qualifier("source1DataSource") DataSource source1DataSource) {
        DataSourceTransactionManager transactionManagerBean = new DataSourceTransactionManager();
        transactionManagerBean.setDataSource(source1DataSource);
        return transactionManagerBean;
    }

    @Bean("source1SqlSessionFactory")
    @Primary
    public SqlSessionFactoryBean proSqlSessionFactory(@Qualifier("source1DataSource") DataSource source1DataSource) throws IOException {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(source1DataSource);
        sessionFactoryBean.setTypeAliasesPackage(environment.getProperty("mybatis.typeAliasesPackage"));
        sessionFactoryBean.setConfiguration(getMyBatisConfiguration());
        Interceptor[] interceptors = new Interceptor[]{new MybatisInterceptor()};
        sessionFactoryBean.setPlugins(interceptors);
        return sessionFactoryBean;
    }



    /**
     * 以下是数据源2的配置
     */


    @Bean("source2DataSource")
    public DataSource mybatisCenDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        hikariDataSource.setDriverClassName(environment.getProperty("spring.datasource.driverClassName"));
        hikariDataSource.setJdbcUrl(environment.getProperty("spring.datasource.source2.jdbc-url"));
        hikariDataSource.setUsername(environment.getProperty("spring.datasource.source2.username"));
        hikariDataSource.setPassword(environment.getProperty("spring.datasource.source2.password"));
        hikariDataSource.setMaximumPoolSize(Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.maximum-pool-size"))));
        hikariDataSource.setMinimumIdle(Integer.parseInt(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.minimum-idle"))));
        hikariDataSource.setConnectionTimeout(Long.parseLong(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.connection-timeout"))));
        hikariDataSource.setIdleTimeout(Long.parseLong(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.idle-timeout"))));
        hikariDataSource.setMaxLifetime(Long.parseLong(Objects.requireNonNull(environment.getProperty("spring.datasource.hikari.max-lifetime"))));
        hikariDataSource.setConnectionTestQuery(environment.getProperty("spring.datasource.hikari.connection-test-query"));
        hikariDataSource.setAutoCommit(Boolean.getBoolean(environment.getProperty("spring.datasource.hikari.auto-commit")));
        hikariDataSource.setPoolName("source2HikariCP");
        return hikariDataSource;
    }

    @Bean(value = CustomTransactional.SOURCE_2)
    public DataSourceTransactionManager transactionSource2Manager (@Qualifier("source2DataSource") DataSource source2DataSource) {
        DataSourceTransactionManager transactionManagerBean = new DataSourceTransactionManager();
        transactionManagerBean.setDataSource(source2DataSource);
        return transactionManagerBean;
//        PlatformTransactionManager
    }

    @Bean("source2SqlSessionFactory")
    public SqlSessionFactoryBean cenSqlSessionFactory(@Qualifier("source2DataSource") DataSource source2DataSource) {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(source2DataSource);
        sessionFactoryBean.setTypeAliasesPackage(environment.getProperty("mybatis.typeAliasesPackage"));
        sessionFactoryBean.setConfiguration(getMyBatisConfiguration());
        Interceptor[] interceptors = new Interceptor[]{new MybatisInterceptor()};
        sessionFactoryBean.setPlugins(interceptors);
        return sessionFactoryBean;
    }

    /**
     * 加载application.yml配置文件里面的mybatis下面configuration的相关配置
     *
     * @author 罗贤超
     */
    private static org.apache.ibatis.session.Configuration getMyBatisConfiguration() {
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setCacheEnabled(true);
        configuration.setUseGeneratedKeys(true);
        configuration.setDefaultExecutorType(ExecutorType.SIMPLE);
        configuration.setCallSettersOnNulls(true);
        configuration.setMapUnderscoreToCamelCase(true);
        try {
            Class<? extends Log> logImpl  = (Class<? extends Log>) Class.forName("org.apache.ibatis.logging.slf4j.Slf4jImpl");
            configuration.setLogImpl(logImpl);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return configuration;
    }

}
