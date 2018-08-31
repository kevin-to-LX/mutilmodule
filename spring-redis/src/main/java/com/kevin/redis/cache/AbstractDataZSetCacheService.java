package com.kevin.redis.cache;

import com.alibaba.fastjson.JSON;
import com.kevin.redis.dto.ZsetCacheVo;
import com.kevin.redis.dto.ZsetItemVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisTemplate;

import java.lang.reflect.ParameterizedType;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @version 1.0.1
 * Created by jinyugai on 2018/8/28.
 */
public abstract class AbstractDataZSetCacheService<V> implements DataCacheService<ZsetCacheVo<V>> {
    private static final Logger log = LoggerFactory.getLogger(AbstractDataZSetCacheService.class);

    protected Class<V> objectClass = (Class<V>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0];


    @Autowired
    protected RedisTemplate<String, ZsetCacheVo> redisTemplate;

    public AbstractDataZSetCacheService() {
        super();
    }


    /**
     * 初始化数据
     * @return
     */
    @Override
    public int init() {
        List<ZsetCacheVo<V>> scoreData = getSingleScoreInitData();
        List<ZsetCacheVo<V>> keyData = getSingleKeyInitData();
        int result = 0;
        if(scoreData != null && !scoreData.isEmpty()){
            addBatch(scoreData);
            result += scoreData.size();
        }
        if(keyData != null && !keyData.isEmpty()){
            addSingleKeyBatch(keyData);
            result += keyData.size();
        }
        return result;
    }

    /**
     * 清除带有所获取前缀的所有数据
     * @return
     */
    @Override
    public int clear() {
        try {
            if(redisTemplate == null) return -1;
            Set<String> keys = redisTemplate.keys(getKeyPre() + "*");
            if(!keys.isEmpty()){
                redisTemplate.delete(keys);
                return keys.size();
            }
        } catch (Exception e){
            log.error("",e);
            return -1;
        }
        return 0;
    }

    /**
     *
     * @param key 键
     * @return
     */
    @Override
    public int delete(String key) {
        try {
            if(redisTemplate == null) return -1;
            redisTemplate.delete(getKeyPre() + "_" + key);
            return 1;
        } catch (Exception e){
            log.error("delete:", e);
            return -1;
        }
    }

    /**
     * 批量删除数据
     * @param key
     * @param valueList
     */
    public void deleteValues(String key, List<V> valueList){
        try{
            redisTemplate.executePipelined((RedisConnection connection) -> {
                byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
                for(V v : valueList){
                    byte[] value = JSON.toJSONString(v).getBytes(StandardCharsets.UTF_8);
                    connection.zRem(zSetKey,value);
                }
                return null;
            });
        } catch (Exception e){
            log.error("deleteValues : ", e);
        }
    }

    /**
     * 删除单个数据
     * @param key
     * @param value
     */
    public void deleteSingleValue(String key, V value){
        try {
            redisTemplate.execute((RedisConnection connection) -> {
                byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
                byte[] zSetValue = JSON.toJSONString(value).getBytes(StandardCharsets.UTF_8);
                connection.zRem(zSetKey, zSetValue);
                return null;
            });
        } catch (Exception e) {
            log.error("deleteSingleValue : ", e);
        }
    }

    /**
     * 删除某一分数范围的数据
     * @param key
     * @param beginScore
     * @param endScore
     */
    public void deleteByScore(String key, Double beginScore, Double endScore){
        try {
            redisTemplate.executePipelined((RedisConnection connection) -> {
                byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
                connection.zRemRangeByScore(zSetKey, beginScore, endScore);
                return null;
            });
        } catch (Exception e) {
            log.error("deleteByScore: ",e);
        }
    }

    /**
     * 获取某一分数范围的数据
     * @param key
     * @param beginScore
     * @param endScore
     * @return
     */
    public Set<V> getByScore(String key, Double beginScore, Double endScore) {
        try {
            byte[] zSetKey = (getKeyPre() + "_"+ key).getBytes(StandardCharsets.UTF_8);
            Set<V> set = redisTemplate.execute((RedisConnection connection) -> {
                Set<byte[]> set1 = connection.zRangeByScore(zSetKey, beginScore, endScore);
                Set<V> ss = new HashSet<>(set1.size());
                for (byte[] b : set1) {
                    String sss = new String(b,StandardCharsets.UTF_8);
                    V v = JSON.parseObject(sss, objectClass);
                    ss.add(v);
                }
                return ss;
            });
            return set;
        } catch (Exception e) {
            log.error("getByScore : ", e);
        }
        return null;
    }

    /**
     * 获取多个分数下的所有值
     * @param key
     * @param scores
     * @return
     */
    public Set<V> getByScores(String key, List<Double> scores) {
        try {
            byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
            Set<V> set = redisTemplate.execute((RedisConnection connection) -> {
                Set<V> sss = new HashSet<>();
                for(Double score : scores){
                    Set<byte[]> s = connection.zRangeByScore(zSetKey, score, score);
                    for (byte[] b : s){
                        String ss = new String(b, StandardCharsets.UTF_8);
                        V v = JSON.parseObject(ss, objectClass);
                        sss.add(v);
                    }
                }
                return sss;
            });
            return set;
        } catch (Exception e){
            log.error("getByScores : ", e);
            return null;
        }
    }
    @Override
    public ZsetCacheVo<V> get(String key) {

        return null;
    }


    /**
     * 修改单一对象数据
     * @param zsetCacheVo
     */
    @Override
    public void update(ZsetCacheVo<V> zsetCacheVo) {
        add(zsetCacheVo);
    }

    /**
     * 批量添加单一score下多个数据
     * @param list 缓存list
     */
    @Override
    public void addBatch(List<ZsetCacheVo<V>> list) {

        try{
            redisTemplate.executePipelined((RedisConnection connextion) -> {
                for(ZsetCacheVo<V> zsetCacheVo : list){
                    byte[] key = (getKeyPre() + "_" + getKey(zsetCacheVo)).getBytes(StandardCharsets.UTF_8);
                    Double score = getScore(zsetCacheVo);
                    List<V> itemList = zsetCacheVo.getItemList();
                    for (V v : itemList){
                        byte[] value = JSON.toJSONString(v).getBytes(StandardCharsets.UTF_8);
                        connextion.zAdd(key, score, value);
                    }
                }
                return null;
            });
        } catch (Exception e) {
            log.error("addBatch:",e);
        }
    }

    @Override
    public List<ZsetCacheVo<V>> getAll() {
        return null;
    }

    /**
     * 添加单一key下的多个score和数据
     * @param zsetCacheVo
     */
    @Override
    public void add(ZsetCacheVo<V> zsetCacheVo) {
        try {
            /**
             * doInRedis中的redis操作不会立刻执行
             * 所有redis操作会在connection.closePipeline()之后一并提交到redis并执行，这是pipeline方式的优势
             * 所有操作的执行结果为executePipelined()的返回值
             * */
            redisTemplate.executePipelined((RedisConnection connection) -> {
                byte[] key = (getKeyPre() + "_" + getKey(zsetCacheVo)).getBytes(StandardCharsets.UTF_8);
                List<ZsetItemVo<V>> itemList = zsetCacheVo.getZsetItemVoList();
                for (ZsetItemVo<V> zsetItemVo : itemList){
                    Double score = getScore(zsetItemVo);
                    byte[] value = JSON.toJSONString(zsetItemVo.getValue()).getBytes(StandardCharsets.UTF_8);
                    connection.zAdd(key, score, value);
                }
                return null;
            });
        } catch (Exception e){
            log.error("add : ",e);
        }
    }

    /**
     * 添加同一score的多个数据
     * zAdd
     * @param key
     * @param score
     * @param values
     */
    public void addSingleScore(String key, Double score, List<V> values){
        byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
        try{
            redisTemplate.execute((RedisConnection connection) ->{
                for(V v : values) {
                    byte[] value = JSON.toJSONString(v).getBytes(Charset.forName("UTF-8"));
                    connection.zAdd(zSetKey,score,value);
                }
                return values.size();
            });
        } catch (Exception e) {
            log.error("",e);
        }
    }

    /**
     * 批量添加单一key下的多个score和数据
     * @param list
     */
    public void addSingleKeyBatch(List<ZsetCacheVo<V>> list){
        try {
            redisTemplate.executePipelined((RedisConnection connection) -> {
                for (ZsetCacheVo<V> zsetCacheVo : list){
                    byte[] key = (getKeyPre() + "_" + getKey(zsetCacheVo)).getBytes(StandardCharsets.UTF_8);
                    List<ZsetItemVo<V>> itemVoList = zsetCacheVo.getZsetItemVoList();
                    for (ZsetItemVo<V> zsetItemVo: itemVoList){
                        Double score = getScore(zsetItemVo);
                        byte[] value = JSON.toJSONString(zsetItemVo.getValue()).getBytes(StandardCharsets.UTF_8);
                        connection.zAdd(key, score, value);
                    }
                }
                return null;
            });
        } catch (Exception e){
            log.error("addSingleKeyBatch: ",e);
        }
    }

    /**
     * 添加单一数据
     * @param key
     * @param score
     * @param value
     */
    public void addSingle(String key, Double score, V value){
        byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
        byte[] zSetValue = JSON.toJSONString(value).getBytes(StandardCharsets.UTF_8);
        try {
            redisTemplate.execute((RedisConnection connection) -> {
                connection.zAdd(zSetKey, score, zSetValue);
                return 1L;
            });
        } catch (Exception e){
            log.error("addSingle : ",e);
        }
    }
    /**
     * 根据值获取分数
     * @param key
     * @param v
     * @return
     */
    public double getScore(String key, V v) {
        double score = redisTemplate.execute((RedisConnection connection) -> {
            byte[] zSetKey = (getKeyPre() + "_" + key).getBytes(StandardCharsets.UTF_8);
            byte[] zSetValue = JSON.toJSONString(v).getBytes(StandardCharsets.UTF_8);
            return connection.zScore(zSetKey, zSetValue);
        });
        return score;
    }
    /**
     * 统计某一key下的数据量
     * @param key
     * @return
     */
    public Long countAll(String key) {
        try {
            byte[] zSetKey = (getKeyPre()+"_"+key).getBytes(StandardCharsets.UTF_8);
            return redisTemplate.execute((RedisConnection connection) -> {
                return connection.zCard(zSetKey);
            });
        } catch (Exception e){
            log.error("countAll", e);
            return null;
        }
    }
    /**
     * 统计某一分数段下的数据量
     * @param key
     * @param beginScore
     * @param endScore
     * @return
     */
    public Long countByScore(String key, Double beginScore, Double endScore) {
        try {
            byte[] zSetKey=(getKeyPre()+"_"+key).getBytes(StandardCharsets.UTF_8);
            return redisTemplate.execute((RedisConnection connection) -> {
                return connection.zCount(zSetKey, beginScore, endScore);
            });
        }catch (Exception e) {
            log.error("countByScore : ", e);
            return null;
        }
    }
    /**
     * 每次重启时要初始化到redis中的数据
     * @return
     */
    protected List<ZsetCacheVo<V>> getSingleScoreInitData(){
        return null;
    }

    protected List<ZsetCacheVo<V>> getSingleKeyInitData(){
        return null;
    }

    protected abstract String getKeyPre();

    protected  String getKey(ZsetCacheVo<V> zsetCacheVo){
        return zsetCacheVo.getKey();
    }

    protected Double getScore(ZsetCacheVo<V> zsetCacheVo){
        return zsetCacheVo.getScore();
    }

    protected Double getScore(ZsetItemVo<V> zsetItemVo){
        return zsetItemVo.getScore();
    }

    protected Double getScore(Double score,Integer scoreType){
        return score;
    }
}
