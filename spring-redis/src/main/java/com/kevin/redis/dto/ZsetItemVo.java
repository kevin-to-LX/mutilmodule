package com.kevin.redis.dto;

/**
 * Created by jinyugai on 2018/8/28.
 */
public class ZsetItemVo<V> {

    private Double score;
    private V value;
    private Integer scoreType;

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

    public Integer getScoreType() {
        return scoreType;
    }

    public void setScoreType(Integer scoreType) {
        this.scoreType = scoreType;
    }
}
