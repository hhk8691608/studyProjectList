package com.stylefeng.guns.rest.modular.redis;


import com.alibaba.dubbo.config.annotation.Service;
import com.stylefeng.guns.api.redis.RedisServiceAPI;
import com.stylefeng.guns.api.redis.constant.RedisConst;
import com.stylefeng.guns.api.redis.vo.UserLeaderboardDO;
import com.stylefeng.guns.core.util.ToolUtil;
import com.sun.org.apache.xml.internal.security.utils.JavaUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

@Component
@Service(interfaceClass = RedisServiceAPI.class )
public class RedisServiceImpl implements  RedisServiceAPI{

    @Autowired
    private RedisTemplate redisTemplate;


    @Override
    public String getSmsToken(String mobile) {

        String key = RedisConst.SMS_TOKEN+mobile;
        String value = ToolUtil.getRandomString(6);
        redisTemplate.opsForValue().set(key,value,RedisConst.SMS_LIFE_TIME, TimeUnit.SECONDS);
        return value;
    }

    @Override
    public int checkSmsTokenLife(String mobile,String value) {
        String key = RedisConst.SMS_TOKEN+mobile;
        String cacheValue = (String) redisTemplate.opsForValue().get(key);
        if(cacheValue == null || cacheValue.equals("") ){
            return RedisConst.SMS_TOKEM_EXPIRED;
        }
        if(!cacheValue.equals(value)){
            return RedisConst.SMS_TOKEM_ERROR;
        }
        return RedisConst.SMS_TOKEM_CORRECT;
    }

    @Override
    public void addUserAccess(String mobile) {
            Double score = RedisConst.USER_ACCESS_WEB_TOP_LEN;
            redisTemplate.opsForZSet().incrementScore(RedisConst.USER_ACCESS_WEB_TOP,mobile,score);
    }

    public  List<UserLeaderboardDO>  getTop(){
        Set<ZSetOperations.TypedTuple<String>>  objSet = redisTemplate.opsForZSet().reverseRangeWithScores(RedisConst.USER_ACCESS_WEB_TOP,0,9);
        Iterator<ZSetOperations.TypedTuple<String>> it = objSet.iterator();
        List<UserLeaderboardDO> resultList = new ArrayList<>();
        while (it.hasNext()) {
            ZSetOperations.TypedTuple<String> str = it.next();
            UserLeaderboardDO model = new UserLeaderboardDO();
            model.setScore(str.getScore());
            String tempModel = str.getValue();
            model.setValue(tempModel);
            resultList.add(model);
        }
        return resultList;
    }


    public static void main(String[] args) {

        Double score = 21.0;
        Long value = 25L;
//        Math.abs

    }



}
