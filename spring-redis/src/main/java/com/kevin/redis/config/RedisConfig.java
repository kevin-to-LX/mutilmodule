package com.kevin.redis.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.LoggerFactory;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;

/**
 * Created by jinyugai on 2018/8/28.
 */
@Configuration
public class RedisConfig {

    private static org.slf4j.Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    private StringRedisTemplate template;

    /**
     * @description 自定义的缓存key的生成策略
     *  若想使用这个key  只需要讲注解上keyGenerator的值
     *  设置为keyGenerator即可
     * @return 自定义策略生成的key
     */
    @Bean
    public KeyGenerator keyGenerator(){
        return new KeyGenerator(){
            @Override
            public Object generate(Object target, Method method, Object... params){
                StringBuffer stringBuffer  = new StringBuffer();
                stringBuffer.append(target.getClass().getName());
                stringBuffer.append(method.getName());
                for(Object obj: params){
                    stringBuffer.append(obj.toString());
                }
                return stringBuffer.toString();
            }
        };
    }

    /**
     * RedisTemplate配置
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public RedisCacheManager cacheManager(JedisConnectionFactory jedisConnectionFactory){
        return RedisCacheManager.create(jedisConnectionFactory);
    }

    /**
     * re
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(JedisConnectionFactory jedisConnectionFactory){
        //設置序列化
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);

        //配置redisTemplate
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        RedisSerializer strinfSerializer = new StringRedisSerializer();
        //key序列化
        redisTemplate.setKeySerializer(strinfSerializer);
        //value序列化
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        //key序列化
        redisTemplate.setHashKeySerializer(strinfSerializer);
        //value序列化
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

    /*@Bean
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
    }*/
}
