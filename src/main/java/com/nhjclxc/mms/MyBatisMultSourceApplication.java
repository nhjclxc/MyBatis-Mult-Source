package com.nhjclxc.mms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = {"com.nhjclxc"}, exclude = {DataSourceAutoConfiguration.class,
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class
})
@ServletComponentScan
// 开启异步
@EnableAsync
// 开启任务调度
@EnableScheduling
//开启注解开发AOP功能
@EnableAspectJAutoProxy
public class MyBatisMultSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(MyBatisMultSourceApplication.class, args);
    }
}
