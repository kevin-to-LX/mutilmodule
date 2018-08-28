package com.kevin.redis.consumer;

import org.springframework.data.redis.listener.Topic;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/28.
 */
public interface MessageConsumer {

    /**
     * 处理接收到的信息
     * @param message
     * @param pattern
     */
    //方法名称是MessageListenerAdapter中的默认名称，如果修改，也要修改adapter中的名称
    void handleMessager(String message, String pattern);

    /**
     * 获取主题
     * @return
     */
    List<Topic> getTopic();
}
