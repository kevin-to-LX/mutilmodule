package com.kevin.mq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * Created by jinyugai on 2018/8/23.
 */
@Component
//消息消费者的类上必须加上@Component，
// 或者是@Service，这样的话，
// 消息消费者类就会被委派给Listener类，
// 原理类似于使用SessionAwareMessageListener
// 以及MessageListenerAdapter来实现消息驱动POJO
public class Consumer {

    // 增加对应处理的监听器bean
    @JmsListener(destination = "kevin_test_mq", containerFactory = "queueListenerFactory")
    public void receiveQueue(String text){
        System.out.println(Thread.currentThread().getName()+"Comsumer 接收到的报文为：" + text);
    }
    // 增加对应处理的监听器bean
    @JmsListener(destination = "kevin_test_topic", containerFactory = "topicListenerFactory")
    public void receiveTopic(String text){
        System.out.println(Thread.currentThread().getName()+"Comsumer 接收到的订阅为：" + text);
    }

}
