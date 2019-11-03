package com.mark.dockerproject.service.technology;

import com.mark.dockerproject.constant.redis.Const.Const;
import com.mark.dockerproject.dao.UserDao;
import com.mark.dockerproject.model.User;
import com.mark.dockerproject.service.business.UserService;
import com.mark.dockerproject.utils.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Set;

@Service
public class RedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private UserService userService;

    public Map<String,Object> testSetZSort(int userId){
        User user  = userService.selectUserById(userId);
        int len = new Random().nextInt(100);
        String uuid = GeneralUtil.getUUID32();
        System.out.println("uuid = "+uuid+",len="+len);
        Map<String,Object> result = new HashMap<>();
        redisTemplate.opsForZSet().add(Const.STATISTICAL_LANGDING_AMOUNT,uuid,len);
        return result;
    }

    public Map<String,Object> testGetZSort( String uuid){
        Map<String,Object> result = new HashMap<>();
        Double score = redisTemplate.opsForZSet().score(Const.STATISTICAL_LANGDING_AMOUNT,uuid);
        System.out.println("score = "+score);
        result.put("score",score);
        return result;
    }


    public Map<String,Object> testZSortBySocre( int start, int end){
        Map<String,Object> result = new HashMap<>();
        Set<String> setResult = redisTemplate.opsForZSet().reverseRangeWithScores(Const.STATISTICAL_LANGDING_AMOUNT,start,end);
        result.put("setResult",setResult);
        return result;
    }
}
