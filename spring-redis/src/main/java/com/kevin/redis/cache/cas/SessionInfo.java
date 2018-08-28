package com.kevin.redis.cache.cas;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by jinyugai on 2018/8/28.
 */
public class SessionInfo {
    private String sessionId;
    private String string;

    public SessionInfo(){
    }

    public SessionInfo(String string,String sessionId){
        this.string = string;
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("sessionId", sessionId)
                .append("string", string)
                .toString();
    }

}
