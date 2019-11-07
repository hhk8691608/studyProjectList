package com.mark.dockerproject.api;


import com.mark.dockerproject.DTO.LikedCountDTO;
import com.mark.dockerproject.model.UserLike;
import com.mark.dockerproject.service.business.LikedService;
import com.mark.dockerproject.service.technology.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gateWay")
public class GateWayController {

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LikedService likedService;

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


    @RequestMapping(value="/testSetZSort", method = RequestMethod.GET)
    public Map<String,Object> testSetZSort(@RequestParam("uerId") int userId) {

        Map<String,Object> result = new HashMap<>();
        redisService.testSetZSort(userId);
        return result;
    }

    @RequestMapping(value="/testGetZSort", method = RequestMethod.GET)
    public Map<String,Object> testGetZSort(@RequestParam("uuid")String uuid) {
        Map<String,Object> result =  redisService.testGetZSort(uuid);
        return result;
    }


    @RequestMapping(value="/testZSortBySocre", method = RequestMethod.GET)
    public Map<String,Object> testZSortBySocre(@RequestParam("start")int start,
                                               @RequestParam("end")int end) {
        Map<String,Object> result =  redisService.testZSortBySocre(start,end);
        return result;
    }



    //用户点赞
    @RequestMapping(value="/userLike", method = RequestMethod.GET)
    public Map<String,Object> userLike(@RequestParam("likedUserId")String likedUserId,
                                               @RequestParam("likedPostId")String likedPostId) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        redisService.saveLiked2Redis(likedUserId,likedPostId);
        redisService.incrementLikedCount(likedUserId);
        return result;
    }

//    取消点赞
    @RequestMapping(value="/userUnLike", method = RequestMethod.GET)
    public Map<String,Object> userUnLike(@RequestParam("likedUserId")String likedUserId,
                                       @RequestParam("likedPostId")String likedPostId) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        redisService.unlikeFromRedis(likedUserId,likedPostId);
        redisService.decrementLikedCount(likedUserId);
        return result;
    }

//    获取Redis中存储的所有点赞数据
    @RequestMapping(value="/getUserLikeList", method = RequestMethod.GET)
    public Map<String,Object> getUserLikeList() {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        List<UserLike> list = redisService.getLikedDataFromRedis(0);
        result.put("list",list);
        return result;
    }

    //    获取Redis中存储的点赞用户top
    @RequestMapping(value="/getUserLikeTOP", method = RequestMethod.GET)
    public Map<String,Object> getUserLikeTOP() {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        List<LikedCountDTO> list = redisService.getLikedCountFromRedis(0);
        result.put("list",list);
        return result;
    }


    //同步数据库
    @RequestMapping(value="/syncDB", method = RequestMethod.GET)
    public Map<String,Object> syncDB() {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        likedService.syncDB();
        return result;
    }







}
