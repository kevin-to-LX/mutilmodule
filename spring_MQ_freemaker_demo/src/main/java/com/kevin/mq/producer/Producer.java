package com.kevin.mq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.jms.Destination;

/**
 * Created by jinyugai on 2018/8/23.
 */
@Service("myproducer")
public class Producer {

    @Autowired
    // 也可以注入JmsTemplate，JmsMessagingTemplate对JmsTemplate进行了封装
    private JmsMessagingTemplate jmsMessagingTemplate;

    public void sendMessage(Destination destination, final String message){
        jmsMessagingTemplate.convertAndSend(destination,message);
    }


    @JmsListener(destination = "out_queue")
    public void consumeMessage(String text){
        System.out.println("从out_queue中获取的到的回复报文是:" + text);
    }
}
