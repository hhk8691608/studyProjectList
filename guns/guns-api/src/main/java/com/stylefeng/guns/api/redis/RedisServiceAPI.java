package com.stylefeng.guns.api.redis;

import com.stylefeng.guns.api.redis.vo.UserLeaderboardDO;

import java.util.List;
import java.util.Set;

public interface RedisServiceAPI {


   String getSmsToken(String mobile);

   int checkSmsTokenLife(String mobile,String value);

   void addUserAccess(String mobile);//用户访问次数增加

   List<UserLeaderboardDO> getTop();//访问排行榜

}
