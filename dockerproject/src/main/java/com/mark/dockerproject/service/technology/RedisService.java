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

    /**
     * 1、每次用户浏览页面的时候，程序需都会对用户存储在登录散列里面的信息进行更新，
     * 2、并将用户的令牌和当前时间戳添加到记录最近登录用户的集合里。
     * 3、如果用户正在浏览的是一个商品，程序还会将商品添加到记录这个用户最近浏览过的商品有序集合里面，
     * 4、如果记录商品的数量超过25个时，对这个有序集合进行修剪。
     */
    public void updateToken(String token,String user,String item){

        long timestamp = System.currentTimeMillis() / 1000;
        redisTemplate.opsForHash().put(Const.LOGIN_USER_TOKENS,token,user);
        redisTemplate.opsForZSet().add(Const.LOGIN_RECENT_USER,token,Const.STATISTICAL_LANGDING_LEN_D);
        if(item != null){
            //4、记录用户浏览过的商品
            redisTemplate.opsForZSet().add(Const.USER_VIEWED+token,item,Const.STATISTICAL_LANGDING_LEN_D);
            //5、移除旧记录，只保留用户最近浏览过的25个商品
            redisTemplate.opsForZSet().removeRange(Const.USER_VIEWED+token,0,-4);
//            redisTemplate.opsForZSet().

        }

    }



    /**
     * 使用cookie实现购物车——就是将整个购物车都存储到cookie里面，
     * 优点：无需对数据库进行写入就可以实现购物车功能，
     * 缺点：怎是程序需要重新解析和验证cookie，确保cookie的格式正确。并且包含商品可以正常购买
     * 还有一缺点：因为浏览器每次发送请求都会连cookie一起发送，所以如果购物车的体积较大，
     * 那么请求发送和处理的速度可能降低。
     * -----------------------------------------------------------------
     * 1、每个用户的购物车都是一个散列，存储了商品ID与商品订单数量之间的映射。
     * 2、如果用户订购某件商品的数量大于0，那么程序会将这件商品的ID以及用户订购该商品的数量添加到散列里。
     * 3、如果用户购买的商品已经存在于散列里面，那么新的订单数量会覆盖已有的。
     * 4、相反，如果某用户订购某件商品数量不大于0，那么程序将从散列里移除该条目
     * 5、需要对之前的会话清理函数进行更新，让它在清理会话的同时，将旧会话对应的用户购物车也一并删除。
     */
    public void addToCart(String token, String item, int count) {
        if (count <= 0) {
            //1、从购物车里面移除指定的商品
            redisTemplate.opsForHash().delete(Const.USER_CART + token, item);
        } else {
            //2、将指定的商品添加到购物车
            redisTemplate.opsForHash().put(Const.USER_CART + token, item, String.valueOf(count));
            Double scoreLen = redisTemplate.opsForZSet().score(Const.USER_VIEWED+token,item);
            if(scoreLen != null){
                scoreLen = scoreLen > 0 ? Const.STATISTICAL_LANGDING_LEN_D+scoreLen : Const.STATISTICAL_LANGDING_LEN_D;
            }else{
                scoreLen = 1.0;
            }

            //4、记录用户浏览过的商品
            redisTemplate.opsForZSet().add(Const.USER_VIEWED+token,item,scoreLen);
            //5、移除旧记录，只保留用户最近浏览过的25个商品
            redisTemplate.opsForZSet().removeRange(Const.USER_VIEWED+token,0,-31);
        }

    }

    //获取具体用户心意产品排行榜TOP
    public Set<String> userLikeItemTOP(String token,int end){
        Set<String> stringSet = redisTemplate.opsForZSet().reverseRange(Const.USER_VIEWED+token,0,end);
        return stringSet;
    }

    //用户点击浏览商品
    public void browseTheGoods(String token,String item){
        Double scoreLen = redisTemplate.opsForZSet().score(Const.USER_VIEWED+token,item);
        if(scoreLen != null){
            scoreLen = scoreLen > 0 ? Const.STATISTICAL_LANGDING_LEN_D+scoreLen : Const.STATISTICAL_LANGDING_LEN_D;
        }else{
            scoreLen = 1.0;
        }
        redisTemplate.opsForZSet().add(Const.USER_VIEWED+token,item,scoreLen);
    }

    //清除下单后购物车的商品
    public void removeCart2Order(String token,String item){

        redisTemplate.opsForHash().delete(Const.USER_CART+token,item);
        browseTheGoods(token,item);
    }






    public void test(String token,int end){
//        redisTemplate.opsForZSet().removeRange(Const.USER_VIEWED+token,0,end);



    }









































}
