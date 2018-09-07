package com.kevin.mq.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

/**
 *  @author kevin
 * Created by jinyugai on 2018/8/29.
 */
public class RedisListService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    /**
     * 将一个值插入到列表头部
     * @param key 列表名称
     * @param value 值
     */
    public Long lPush(String key, String value){
        return redisTemplate.opsForList().leftPush(key, value);
    }

    /**
     * 移出并获取列表的第一个元素
     * @param key 列表名称
     */
    public String lPop(String key){
        return redisTemplate.opsForList().leftPop(key);
    }

    /**
     * 将一个值插入到列表尾部
     * @param key 列表名称
     * @param value 值
     */
    public Long rPush(String key, String value){
        return redisTemplate.opsForList().rightPush(key,value);
    }

    /**
     * 移出并获取列表的最后一个元素
     * @param key 列表名称
     * @return 值
     */
    public String rPop(String key){
        return redisTemplate.opsForList().rightPop(key);
    }

    /**
     * 列表长度
     * @param key 列表名称
     * @return 值
     */
    public long size(String key){
        return redisTemplate.opsForList().size(key);
    }
    /**
     * 返回列表中指定区间内的元素，区间以偏移量 START 和 END 指定。 其中 0 表示列表的第一个元素，
     * 1 表示列表的第二个元素，以此类推。 你也可以使用负数下标，
     * 以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 假如你有一个包含一百个元素的列表，对该列表执行 LRANGE list 0 10 ，
     * 结果是一个包含11个元素的列表，
     * 这表明 stop 下标也在 LRANGE 命令的取值范围之内(闭区间)
     * 对应redis —— lrange key start end
     * @param key 列表名称
     * @param start 开始位置
     * @param end 结束位置
     */
    public List<String> range(String key, int start, int end){
        return redisTemplate.opsForList().range(key, start, end);
    }
    /**
     * 获取列表所有元素
     * @param key 列表名称
     */
    public List<String> getAll(String key){
        return range(key,0,-1);
    }

    /**
     * Redis Lrem 根据参数 count 的值，移除列表中与参数 VALUE 相等的元素。
     * @param key 列表名称
     * @param count 数量
     *      count 的值可以是以下几种：
     *          count > 0 : 从表头开始向表尾搜索，移除与 VALUE 相等的元素，数量为 COUNT 。
     *          count < 0 : 从表尾开始向表头搜索，移除与 VALUE 相等的元素，数量为 COUNT 的绝对值。
     *          count = 0 : 移除表中所有与 VALUE 相等的值。
     * @param value 值
     */
    public void remove(String key, long count,String value){
        redisTemplate.opsForList().remove(key, count, value);
    }

    /**
     * LINDEX key index
     * 返回列表 key 中，下标为 index 的元素。
     * 下标(index)参数 start 和 stop 都以 0 为底，也就是说，以 0 表示列表的第一个元素，以 1 表示列表的第二个元素，以此类推。
     * 你也可以使用负数下标，以 -1 表示列表的最后一个元素， -2 表示列表的倒数第二个元素，以此类推。
     * 如果 key 不是列表类型，返回一个错误。
     * @param key
     * @param index
     * @return
     */
    public String index(String key,long index){
        return redisTemplate.opsForList().index(key, index);
    }


    /**
     *
     * 设置指定位置值
     * @param key 列表名称
     * @param index 索引
     * @param value 值
     */
    public void set(String key, long index, String value){
        redisTemplate.opsForList().set(key,index,value);
    }
    /**
     * 裁剪，列表保留指定区间元素
     * @param key 列表名称
     * @param start 开始位置
     * @param end 结束位置
     */
    public void trim(String key, long start, int end){
        redisTemplate.opsForList().trim(key, start, end);
    }
}

