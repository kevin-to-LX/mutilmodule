package com.kevin.redis.config;

import com.kevin.redis.consumer.MessageConsumer;
import com.kevin.redis.utils.AppContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.Topic;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jinyugai on 2018/8/31.
 */
/*@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds= 1800)
@PropertySource("classpath:redis.properties")*/
public class RedisConfig {
    private static Logger logger= LoggerFactory.getLogger(RedisConfig.class);
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.pool.maxActive}")
    private int maxActive;
    @Value("${spring.redis.pool.maxWait}")
    private long maxWait;
    @Value("${spring.redis.pool.maxIdle}")
    private int maxIdle;
    @Value("${spring.redis.pool.minIdle}")
    private int minIdle;
    @Value("${spring.redis.timeout}")
    private int timeout;

    @Bean
    public JedisPoolConfig getRedisConfig() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMinIdle(minIdle);
        jedisPoolConfig.setMaxTotal(maxActive);
        return jedisPoolConfig;
    }

    @Bean
    //@ConfigurationProperties(prefix = "spring.redis")
    public JedisConnectionFactory getConnectionFactory(){
        JedisConnectionFactory factory=new JedisConnectionFactory();
        JedisPoolConfig config=getRedisConfig();
        factory.setPoolConfig(config);
        factory.setHostName(host);
        factory.setTimeout(timeout);
        factory.setPassword(password);
        factory.setPort(port);
        logger.info("JedisConnectionFactory bean init success");
        return factory;
    }

    @Bean
    public RedisTemplate<?,?> getRedisTemplate(){
        return new StringRedisTemplate(getConnectionFactory());
    }

    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(JedisConnectionFactory jedisConnectionFactory){
        RedisMessageListenerContainer redisMessageListenerContainer=new RedisMessageListenerContainer();
        redisMessageListenerContainer.setConnectionFactory(jedisConnectionFactory);
        Map<MessageListener, Collection<? extends Topic>> listenerTopicMap=new HashMap<>();
        Map<String, MessageConsumer> consumerMap= AppContext.getBeansOfType(MessageConsumer.class);
        for(MessageConsumer messageConsumer:consumerMap.values()){
            MessageListenerAdapter messageListenerAdapter=new MessageListenerAdapter();
            messageListenerAdapter.setDelegate(messageConsumer);
            messageListenerAdapter.afterPropertiesSet();
            listenerTopicMap.put(messageListenerAdapter, messageConsumer.getTopic());
        }
        redisMessageListenerContainer.setMessageListeners(listenerTopicMap);
        return redisMessageListenerContainer;
    }
}
