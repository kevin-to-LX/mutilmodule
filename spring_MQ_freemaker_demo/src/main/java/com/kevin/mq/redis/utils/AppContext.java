package com.kevin.mq.redis.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by chenzhaoming on 2017/4/24.
 */
@Component
public class AppContext implements ApplicationContextAware {

    // Spring应用上下文环境
    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量.
     */
    /*实现了ApplicationContextAware 接口，必须实现该方法；通过传递applicationContext参数初始化成员变量applicationContext*/
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }

    /**
     * 取得存储在静态变量中的ApplicationContext.
     */
    public static ApplicationContext getApplicationContext() {
        checkApplicationContext();
        return applicationContext;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) {
        checkApplicationContext();
        if (applicationContext.containsBean(name)) {
            return (T) applicationContext.getBean(name);
        }
        return null;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     */
    @SuppressWarnings("unchecked")
    public static <T> Map<String,T> getBeansOfType(Class<T> clazz) {
        checkApplicationContext();
        return applicationContext.getBeansOfType(clazz);
    }

    private static void checkApplicationContext() {
        if (applicationContext == null)
            throw new IllegalStateException("applicationContext未注入");
    }

    /**
     * 同步方法注册bean到ApplicationContext中
     *
     * @param beanName
     * @param clazz
     * @param original bean的属性值
     */
    public static synchronized void setBean(String beanName, Class<?> clazz,Map<String,Object> original) {
        checkApplicationContext();
        DefaultListableBeanFactory beanFactory = (DefaultListableBeanFactory) applicationContext.getAutowireCapableBeanFactory();
        if(beanFactory.containsBean(beanName)){
            removeBean(beanName);
        }
        //BeanDefinition beanDefinition = new RootBeanDefinition(clazz);
        GenericBeanDefinition definition = new GenericBeanDefinition();
        //类class
        definition.setBeanClass(clazz);
        //属性赋值
        definition.setPropertyValues(new MutablePropertyValues(original));
        //注册到spring上下文
        beanFactory.registerBeanDefinition(beanName, definition);
    }

    /**
     * 删除spring中管理的bean
     * @param beanName
     */
    public static void removeBean(String beanName){
        ApplicationContext ctx = AppContext.getApplicationContext();
        DefaultListableBeanFactory acf = (DefaultListableBeanFactory) ctx.getAutowireCapableBeanFactory();
        if(acf.containsBean(beanName)) {
            acf.removeBeanDefinition(beanName);
        }
    }

}
