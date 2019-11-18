package com.mark.dockerproject.api;


import com.mark.dockerproject.DTO.LikedCountDTO;
import com.mark.dockerproject.DTO.OrderDTO;
import com.mark.dockerproject.dao.ItemDao;
import com.mark.dockerproject.model.Item;
import com.mark.dockerproject.model.UserLike;
import com.mark.dockerproject.mq.kafka.KafkaContants;
import com.mark.dockerproject.mq.kafka.KafkaSender;
import com.mark.dockerproject.service.business.ItemService;
import com.mark.dockerproject.service.business.LikedService;
import com.mark.dockerproject.service.business.OrderService;
import com.mark.dockerproject.service.business.PayService;
import com.mark.dockerproject.service.technology.RedisService;
import com.mark.dockerproject.utils.DESUtils;
import com.mark.dockerproject.zk.api.DistributedLockByCurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/gateWay")
public class GateWayController {


    private static final Logger LOG = LoggerFactory.getLogger(GateWayController.class);

    @Autowired
    private DistributedLockByCurator distributedLockByZookeeper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RedisService redisService;

    @Autowired
    private LikedService likedService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ItemService itemService;

    @Autowired
    private PayService payService;

    @Autowired
    private KafkaSender kafkaSender;



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


    @RequestMapping(value="/testLogin", method = RequestMethod.GET)
    public Map<String,Object> test_login() {

        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
            String token = redisService.generateToken();
            result.put("token",token);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/testGet", method = RequestMethod.GET)
    public Map<String,Object> test_get(@RequestParam("token")String token) {

        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
            String key = redisService.checkToken(token);
            result.put("token",key);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/browseProduct", method = RequestMethod.GET)
    public Map<String,Object> browseProduct(@RequestParam("token")String token,
                                              @RequestParam(value = "item")String item) {
        Map<String,Object> result = new HashMap<>();
        try {
            result.put("code",200);
            redisService.browseTheGoods(token,item);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            result.put("code",500);
            return result;
        }
    }



    @RequestMapping(value="/addCard", method = RequestMethod.GET)
    public Map<String,Object> addCard(@RequestParam("token")String token,
                                      @RequestParam("item")String item,
                                      @RequestParam("count")int count) {
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
            redisService.addToCart(token,item,count);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/mindItemTOP", method = RequestMethod.GET)
    public Map<String,Object> userLikeItemTOP(@RequestParam("token")String token,
                                      @RequestParam(value = "end",defaultValue = "10")int end) {
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
            Set<String> stringSet = redisService.userLikeItemTOP(token,end);
            result.put("stringSet",stringSet);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value="/order", method = RequestMethod.POST)
    public Map<String,Object> cart2Order(@RequestBody OrderDTO orderDTO) {
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
            orderService.saveOrder(orderDTO);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    @RequestMapping(value="/pay", method = RequestMethod.GET)
    public Map<String,Object> order2pay(@RequestParam(value = "accountId",defaultValue = "M11245132")String accountId,
                                        @RequestParam(value = "orderId",defaultValue = "M12")String orderId,
                                        @RequestParam(value = "userId",defaultValue = "222")String userId,
                                        @RequestParam(value = "money",defaultValue = "222")double money) {
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
            String codeStr = payService.payment(userId,orderId,accountId,money);
            result.put("msg",codeStr);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }





    @RequestMapping(value="/testAPI", method = RequestMethod.GET)
    public Map<String,Object> test(@RequestParam(value = "accountId",defaultValue = "M11245132")String accountId,
                                   @RequestParam(value = "orderId",defaultValue = "M12")String orderId,
                                   @RequestParam(value = "userId",defaultValue = "222")String userId,
                                   @RequestParam(value = "money",defaultValue = "222")double money,
                                   @RequestParam(value = "currentVersion",defaultValue = "11")int currentVersion) {
        try {
            Map<String,Object> result = new HashMap<>();
            result.put("code",200);
//            payService.paySuccess2Order(orderId,userId);
//            String userId, String orderId, String accountId, double money
//            payService.payment(userId,orderId,accountId,money);
            payService.testAPI(accountId,currentVersion);
            return result;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private final static String PATH = "test";

    @RequestMapping(value="/lock1", method = RequestMethod.GET)
    public Boolean getLock1() {
        Boolean flag;
        distributedLockByZookeeper.acquireDistributedLock(PATH);
        try {
            LOG.info("I am lock1，i am updating resource……！！！");
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
        }
        return flag;
    }

    @RequestMapping(value="/lock2", method = RequestMethod.GET)
    public Boolean getLock2() {
        Boolean flag;
        distributedLockByZookeeper.acquireDistributedLock(PATH);
        try {
            LOG.info("I am lock2，i am updating resource……！！！");
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            flag = distributedLockByZookeeper.releaseDistributedLock(PATH);
        }
        return flag;
    }




    @RequestMapping(value="/sendMessageByKafka", method = RequestMethod.GET)
    public Map<String,Object> sendMessageByKafka(@RequestParam(value = "messageStr",defaultValue = "hello,kafka")String messageStr) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",200);
        kafkaSender.sendMessage(KafkaContants.TRADE_TOPIC,messageStr);
        return  result;
    }


    public static void main(String[] args) {




    }







}
