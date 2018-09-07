package com.kevin.mq.redis.dto;

import java.util.List;

/**
 * Created by jinyugai on 2018/8/28.
 */
public class ZsetCacheVo<V> {
    private String key;
    private Double score;
    private V value;
    private List<V> itemList;
    private List<ZsetItemVo<V>> zsetItemVoList;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public List<V> getItemList() {
        return itemList;
    }

    public void setItemList(List<V> itemList) {
        this.itemList = itemList;
    }

    public List<ZsetItemVo<V>> getZsetItemVoList() {
        return zsetItemVoList;
    }

    public void setZsetItemVoList(List<ZsetItemVo<V>> zsetItemVoList) {
        this.zsetItemVoList = zsetItemVoList;
    }
}
