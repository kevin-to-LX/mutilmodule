package com.kevin.mq.redis.cache.cas;



/**
 * Created by chenzhaoming on 2018/1/9.
 */
public class SessionInfo {
    private String sessionId;
    private String st;

    public SessionInfo() {
    }

    public SessionInfo(String st, String sessionId) {
        this.st = st;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSt() {
        return st;
    }

    public void setSt(String st) {
        this.st = st;
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("sessionId ："+ sessionId)
                .append("st ："+st)
                .toString();
    }
}
