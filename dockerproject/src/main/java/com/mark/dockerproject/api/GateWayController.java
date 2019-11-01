package com.mark.dockerproject.api;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gateWay")
public class GateWayController {

    @Autowired
    private RedisTemplate redisTemplate;

    @RequestMapping(value="/getSMSValidateCode", method = RequestMethod.GET)
    public Map<String,Object> getSMSValidateCode(@RequestParam("mobile")String mobile) {
        int rambom =  (int) ((Math.random() * 9 + 1) * 100000);
        redisTemplate.opsForValue().set(mobile,rambom+"");
        redisTemplate.expire(mobile,30,TimeUnit.SECONDS);
        Map<String,Object> result = new HashMap<>();
        result.put("rambom",rambom);
        return result;
    }



    @RequestMapping(value="/login", method = RequestMethod.GET)
    public Map<String,Object> login(@RequestParam("mobile")String mobile,@RequestParam("rambom")String rambom) {
        Map<String,Object> result = new HashMap<>();
        String redisResult = (String) redisTemplate.opsForValue().get(mobile);
        if(redisResult == null){
            result.put("msg","该验证码已过期");
        }
        else if(rambom != null && redisResult.equals(rambom)){
            result.put("msg","登录成功");
        }else {
            result.put("msg","该验证码不正确");
        }

        return result;
    }




}
