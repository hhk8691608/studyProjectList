package com.stylefeng.guns.api.redis.vo;

import lombok.Data;

import java.io.Serializable;
@Data
public class UserLeaderboardDO implements Serializable {

    private long id;
    private String value;
    private Double score;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
