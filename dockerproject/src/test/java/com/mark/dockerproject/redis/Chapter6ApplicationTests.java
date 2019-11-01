package com.mark.dockerproject.redis;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import com.mark.dockerproject.DTO.User;

import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Chapter6ApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisTest() {
        // redis存储数据
        String key = "name";
        redisTemplate.opsForValue().set(key, "yukong");
        // 获取数据
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("获取缓存中key为" + key + "的值为：" + value);

        User user = new User();
        user.setUsername("yukong");
        user.setSex(18);
        user.setId(1L);
        String userKey = "yukong";
        redisTemplate.opsForValue().set(userKey, user);
        User newUser = (User) redisTemplate.opsForValue().get(userKey);
        System.out.println("获取缓存中key为" + userKey + "的值为：" + newUser);

    }


    @Test
    public void redisTest1() {
        String key = "jy";

        redisTemplate.opsForValue().set(key,"jiayou");

        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("缓存中key="+key+",值为"+value);
    }



    @Test
    public void redisTest_expire() {
        String key = "zd";

        redisTemplate.opsForValue().set(key,"zhidao");
        redisTemplate.expire(key,20,TimeUnit.SECONDS);
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println("缓存中key="+key+",值为"+value);
    }



}
