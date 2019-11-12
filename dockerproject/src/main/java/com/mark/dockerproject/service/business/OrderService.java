package com.mark.dockerproject.service.business;

import com.mark.dockerproject.Const.MQConst;
import com.mark.dockerproject.DTO.ItemDto;
import com.mark.dockerproject.DTO.OrderDTO;
import com.mark.dockerproject.dao.ItemDao;
import com.mark.dockerproject.dao.OrderDao;
import com.mark.dockerproject.dao.UserDao;
import com.mark.dockerproject.dao.UserLikeDao;
import com.mark.dockerproject.model.Item;
import com.mark.dockerproject.model.Order;
import com.mark.dockerproject.mq.orderlyMq.OrderlyProducer;
import com.mark.dockerproject.service.business.mq.CallbackService;
import com.mark.dockerproject.service.technology.RedisService;
import com.mark.dockerproject.utils.FastJsonConvertUtil;
import com.mark.dockerproject.utils.GeneralUtil;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderService {

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;

    @Autowired
    OrderDao orderDao;

    @Autowired
    ItemDao itemDao;

    @Autowired
    private OrderlyProducer orderlyProducer;

    @Autowired
    private PayService payService;



    public void saveOrder(OrderDTO orderDTO){

        String token = orderDTO.getToken();
        List<ItemDto> itemList = orderDTO.getItemDtoList();
        for(ItemDto itemDto : itemList){
            Item dbItem = itemDao.findItemById(itemDto.getId());
            if(dbItem != null){
                //清除用户购物车此商品
                redisService.removeCart2Order(token,(dbItem.getId()+""));
                //TODO 1.扣库存
                //TODO 2.分布式锁 高并发
                //TODO 3.订单流转如快递信息等 引入消息中间件



                //TODO 4.扣费问题--分布式事务问题

            }
        }
        String orderNo = GeneralUtil.ramdom9()+"";
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setPrice(orderDTO.getPrice());
        order.setSupplierId(orderDTO.getSupplierId());
        orderDao.save(orderDTO.getPrice(),orderNo);


        //TODO 扣费--分布式事务
        int status = 1;


        //包裹派发
        orderlyProducerSend(orderNo,order.getId()+"",orderDTO.getSupplierId());


    }

    private void payment(String userId, String orderId, String accountId, double money){
        payService.payment(userId,orderId,accountId,money);
    }




    //物流---包裹派发
    private void orderlyProducerSend(String orderNo,String orderId,String supplierId){
        List<Message> messageList = new ArrayList<>();
        Map<String, Object> param1 = new HashMap<String, Object>();
        param1.put("orderNo",orderNo);
        param1.put("orderId", orderId);
        param1.put("text", "创建包裹操作---1");

        String key1 = UUID.randomUUID().toString() + "$" +System.currentTimeMillis();
        Message message1 = new Message(MQConst.PKG_TOPIC, MQConst.PKG_TAGS, key1, FastJsonConvertUtil.convertObjectToJSON(param1).getBytes());
        messageList.add(message1);

        Map<String, Object> param2 = new HashMap<String, Object>();
        param2.put("orderNo",orderNo);
        param2.put("orderId", orderId);
        param2.put("text", "发送物流通知操作---2");

        String key2 = UUID.randomUUID().toString() + "$" +System.currentTimeMillis();
        Message message2 = new Message(MQConst.PKG_TOPIC, MQConst.PKG_TAGS,key2, FastJsonConvertUtil.convertObjectToJSON(param2).getBytes());

        messageList.add(message2);


        //顺序消费订单
        int messageQueueNumber = Integer.parseInt( supplierId );
        orderlyProducer.sendOrderlyMessages(messageList,messageQueueNumber);
    }



}
