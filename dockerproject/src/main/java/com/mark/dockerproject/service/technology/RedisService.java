package com.mark.dockerproject.service.technology;

import com.mark.dockerproject.Const.LikedStatusEnum;
import com.mark.dockerproject.DTO.LikedCountDTO;
import com.mark.dockerproject.constant.redis.Const.Const;
import com.mark.dockerproject.dao.UserDao;
import com.mark.dockerproject.model.User;
import com.mark.dockerproject.model.UserLike;
import com.mark.dockerproject.service.business.UserService;
import com.mark.dockerproject.utils.GeneralUtil;
import com.mark.dockerproject.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.*;

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

//    点赞功能
    //点赞。状态为1
    public void saveLiked2Redis(String likedUserId, String likedPostId){
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,LikedStatusEnum.LIKE.getCode());
    }
    //取消点赞。将状态改变为0
    public void unlikeFromRedis(String likedUserId, String likedPostId) {
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_USER_LIKED,key,LikedStatusEnum.UNLIKE.getCode());
    }

    /**
     * 从Redis中删除一条点赞数据
     * @param likedUserId
     * @param likedPostId
     */
    public void deleteLikedFromRedis(String likedUserId, String likedPostId){
        String key = RedisKeyUtils.getLikedKey(likedUserId, likedPostId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
    }

    /**
     * 该用户的点赞数加1
     * @param likedUserId
     */
    public void incrementLikedCount(String likedUserId){
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,1);
    }

    /**
     * 该用户的点赞数减1
     * @param likedUserId
     */
    public void decrementLikedCount(String likedUserId){
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT,likedUserId,-1);
    }

    /**
     * 获取Redis中存储的所有点赞数据
     * @return
     */
    public List<UserLike> getLikedDataFromRedis(int type){
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED, ScanOptions.NONE);
        List<UserLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            //分离出 likedUserId，likedPostId
            String[] split = key.split("::");
            String likedUserId = split[0];
            String likedPostId = split[1];
            Integer value = (Integer) entry.getValue();

            //组装成 UserLike 对象
            UserLike userLike = new UserLike(likedUserId, likedPostId, value);
            list.add(userLike);

            //存到 list 后从 Redis 中删除
            if(type == 1){
                redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED, key);
            }

        }

        return list;

    }

    /**
     * 获取Redis中存储的所有点赞数量
     * @return
     */
    public List<LikedCountDTO> getLikedCountFromRedis(int type){
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, ScanOptions.NONE);
        List<LikedCountDTO> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object, Object> map = cursor.next();
            //将点赞数量存储在 LikedCountDT
            String key = (String)map.getKey();
            LikedCountDTO dto = new LikedCountDTO(key, (Integer) map.getValue());
            list.add(dto);
            //从Redis中删除这条记录
            if(type ==1){
                   redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_USER_LIKED_COUNT, key);
            }

        }
        return list;

    }




    /**
     * 使用Redis重新实现登录cookie，取代目前由关系型数据库实现的登录cookie功能
     * 1、将使用一个散列来存储登录cookie令牌与与登录用户之间的映射。
     * 2、需要根据给定的令牌来查找与之对应的用户，并在已经登录的情况下，返回该用户id。
     */
    public String checkToken(String key){
        Object obj = redisTemplate.opsForHash().get(Const.LOGIN_USER_TOKENS,key);
        return obj==null?null:obj.toString();
    }

    public String generateToken() throws Exception {
        String uuid32 = GeneralUtil.getUUID32();
        if(checkToken(uuid32) != null){
            throw new Exception("主键冲突...");
        }
        redisTemplate.opsForHash().put(Const.LOGIN_USER_TOKENS,uuid32,uuid32);
        return uuid32;
    }


















}
