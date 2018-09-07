package com.kevin.mq.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;

import javax.jms.ConnectionFactory;
import java.util.concurrent.Executors;

/**
 * Created by jinyugai on 2018/8/23.
 * 为了让queue和topic同时生效
 */
@Configuration
@EnableJms
public class JmsConfig {

    @Bean
    public JmsListenerContainerFactory<?> topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(true);
//      表示该Listener负责处理Topic
        factory.setConnectionFactory(connectionFactory);
        factory.setTaskExecutor(Executors.newFixedThreadPool(6));
        factory.setConcurrency("5");
        return factory;
    }

    @Bean
    public JmsListenerContainerFactory<?> queueListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setPubSubDomain(false);
//      该Listener主要负责处理Queue
//      jms默认就是queue模式
        factory.setConnectionFactory(connectionFactory);
        factory.setTaskExecutor(Executors.newFixedThreadPool(6));
        factory.setConcurrency("5");
        return factory;
    }


}
