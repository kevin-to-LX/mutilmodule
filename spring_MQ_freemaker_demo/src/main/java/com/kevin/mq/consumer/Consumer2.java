package com.kevin.mq.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by jinyugai on 2018/8/23.
 */
@Component
public class Consumer2 {

    @JmsListener(destination = "kevin_test_mq", containerFactory = "queueListenerFactory")
    @SendTo("out_queue")
    public String receiveQueue(String text){
        System.out.println("Comsumer2 receive the Msg:" + text);
        return "return message :" + text;
    }
}
