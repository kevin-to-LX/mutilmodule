package com.kevin.redis.lock;

import com.sun.istack.internal.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.concurrent.TimeUnit;


/**
 * Created by jinyugai on 2018/8/31.
 */
public class RedisLock {
    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private RedisTemplate<String, String> redisTemplate;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    /**
     * Lock key path.
     */
    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private  int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    /**
     * Detailed constructor with default acquire timeout 10000 msecs and lock expiration of 60000 msecs.
     *
     * @param lockKey lock key (ex. account:1, ...)
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    /**
     * Detailed constructor with default lock expiration of 60000 msecs.
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs){
        this(redisTemplate,lockKey);
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * Detailed constructor.
     */
    public RedisLock(RedisTemplate<String, String> redisTemplate, String lockKey, int timeoutMsecs, int expireMsecs){
        this(redisTemplate, lockKey, timeoutMsecs);
        this.expireMsecs = expireMsecs;
    }

    public int getExpireMsecs() {
        return expireMsecs;
    }

    public void setExpireMsecs(int expireMsecs) {
        this.expireMsecs = expireMsecs;
    }

    public int getTimeoutMsecs() {
        return timeoutMsecs;
    }

    public void setTimeoutMsecs(int timeoutMsecs) {
        this.timeoutMsecs = timeoutMsecs;
    }

    /**
     * @return lock key
     */
    public synchronized String getLockKey() {
        return lockKey;
    }

    public String get(final String key){
        Object obj = null;
        try {
            obj = (String )redisTemplate.execute(new RedisCallback<Object>() {
                @Nullable
                @Override
                public Object doInRedis(RedisConnection connection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] data = connection.get(serializer.serialize(key));
                    connection.close();
                    if(data == null){
                        return null;
                    }
                    return serializer.deserialize(data);
                }
            });
        } catch (Exception e){
            logger.error("get redis error, key : {}",key);
        }
        return obj != null ? obj.toString() : null;
    }

    private boolean setNX(final String key, final String value){
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {

                @Nullable
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    boolean success = redisConnection.setNX(serializer.serialize(key), serializer.serialize(value));
                    redisConnection.close();
                    return success;
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}",key);
        }
        return obj != null ? (Boolean) obj : false;
    }

    public String getSet(final String key, final String value) {
        Object obj = null;
        try {
            obj = redisTemplate.execute(new RedisCallback<Object>() {

                @Nullable
                @Override
                public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                    StringRedisSerializer serializer = new StringRedisSerializer();
                    byte[] result = redisConnection.getSet(serializer.serialize(key), serializer.serialize(value));
                    redisConnection.close();
                    return serializer.deserialize(result);
                }
            });
        } catch (Exception e) {
            logger.error("setNX redis error, key : {}", key);
        }
        return obj != null ? (String) obj : null;
    }

    /**
     * 获得 lock.
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     *
     * @return true if lock is acquired, false acquire timeouted
     * @throws InterruptedException in case of thread interruption
     */
    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String exiresStr = String.valueOf(expires);
            if (this.setNX(lockKey, exiresStr)){
                //
                locked = true;
                return true;
            }

            String curremtValueStr = this.get(lockKey);
            if(curremtValueStr != null && Long.parseLong(curremtValueStr) < System.currentTimeMillis()) {
                String oldValueStr = this.get(lockKey);
                if (curremtValueStr != null && oldValueStr.equals(curremtValueStr)) {
                    locked = true;
                    return true;
                }
            }
            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;
            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }
        return false;
    }

    /**
     * 获得 lock,不会等待超时
     * 实现思路: 主要是使用了redis 的setnx命令,缓存了锁.
     * reids缓存的key是锁的key,所有的共享, value是锁的到期时间(注意:这里把过期时间放在value了,没有时间上设置其超时时间)
     * 执行过程:
     * 1.通过setnx尝试设置某个key的值,成功(当前没有这个锁)则返回,成功获得锁
     * 2.锁已经存在则获取锁的到期时间,和当前时间比较,超时的话,则设置新的值
     */
    public static RedisLock getLock(RedisTemplate<String, String> redisTemplate, String key, int waitTime, int expireMsecs) {
        RedisLock lock = new RedisLock(redisTemplate, key, 0, expireMsecs);
        logger.info("getLock start");
        while (true) {
            logger.info("getLock start while");
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);//锁到期时间
            if (lock.setNX(lock.getLockKey(), expiresStr)) {
                lock.setLocked(true);
                return lock;
            }
            String currentValueStr = lock.get(lock.getLockKey());//redis里面的时间
            //判断是否为空，不为空的情况下，如果被其他线程设置了值，则第二个条件判断是过不去的
            if (currentValueStr != null && Long.parseLong(currentValueStr) < System.currentTimeMillis()){
                //获取上一个锁到期时间，并设置现在的锁到期时间，
                //只有一个线程才能获取上一个线上的设置时间，因为jedis.getSet是同步的
                String oldValueStr = lock.getSet(lock.getLockKey(), expiresStr);
                if (oldValueStr != null && oldValueStr.equals(currentValueStr)) {
                    //防止误删（覆盖，因为key是相同的）了他人的锁——这里达不到效果，这里值会被覆盖，但是因为什么相差了很少的时间，所以可以接受
                    //[分布式的情况下]:如过这个时候，多个线程恰好都到了这里，但是只有一个线程的设置值和当前值相同，他才有权利获取锁
                    lock.setLocked(true);
                    logger.info("getLock start while return");
                    return lock;
                }
            }
            try {
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
    /**
     * Acqurired lock release.
     */
    public synchronized void unlock() {
        if (locked) {
            String currentValueStr = this.get(lockKey); //redis里面的时间

            if (currentValueStr != null && Long.parseLong(currentValueStr) > System.currentTimeMillis()) {
                redisTemplate.delete(lockKey);
                locked = false;
            }
        }
    }
    /**
     * 获取锁
     * @param key
     * @param seconds
     * @return
     */
    public synchronized boolean onlyLock(String key, long seconds) {
        RedisLock lock = new RedisLock(redisTemplate, key);
        String expiresStr = String.valueOf(System.currentTimeMillis());
        if (lock.setNX(lock.getLockKey(), expiresStr)) {
            redisTemplate.expire(lock.getLockKey(), seconds, TimeUnit.SECONDS);
            return true;
        }
        return false;
    }

    /**
     * 修改key过期时间
     * @param key
     * @param seconds
     */
    public synchronized void expireLockTime(String key, long seconds) {
        RedisLock lock = new RedisLock(redisTemplate,key);
        redisTemplate.expire(lock.getLockKey(),seconds,TimeUnit.SECONDS);
    }
}
