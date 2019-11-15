package com.stylefeng.guns.api.user.vo;

import com.stylefeng.guns.api.redis.vo.UserLeaderboardDO;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserLeaderboardModel implements Serializable {

    private Long id;
    private Double score;
    private String mobile;
    private String userName;

    public UserLeaderboardModel(){}

    public UserLeaderboardModel(UserLeaderboardDO userLeaderboardDO){
        this.score = userLeaderboardDO.getScore();
        this.mobile = userLeaderboardDO.getValue();
    }

}
