package com.nhjclxc.mms.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.util.Map;

/**
 * spring上下文工具类
 */
@Component
public class ApplicationContextHelper implements ApplicationContextAware {

    protected static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T getBeanByClass(Class<T> clazz) {
        return context.getBean(clazz);
    }

    /**
     * 根据名称获取bean
     *
     * @param beanName
     * @return
     */
    public static Object getBeanByName(String beanName) {
        return context.getBean(beanName);
    }

    /**
     * 根据类型获取bean
     *
     * @param clazz
     * @return
     */
    public static <T> Map<String, T> getBeansOfType(Class<T> clazz) {
        return context.getBeansOfType(clazz);
    }

    public static ApplicationContext getContext() {
        return context;
    }

    @PreDestroy
    public void destory() {
        CustomThreadPoolExecutor.destory();
    }
}
