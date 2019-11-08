package com.mark.dockerproject.service.business;

import com.mark.dockerproject.DTO.ItemDto;
import com.mark.dockerproject.DTO.OrderDTO;
import com.mark.dockerproject.dao.ItemDao;
import com.mark.dockerproject.dao.OrderDao;
import com.mark.dockerproject.dao.UserDao;
import com.mark.dockerproject.dao.UserLikeDao;
import com.mark.dockerproject.model.Item;
import com.mark.dockerproject.model.Order;
import com.mark.dockerproject.service.technology.RedisService;
import com.mark.dockerproject.utils.GeneralUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
                //TODO 4.扣费问题

            }
        }
        String orderNo = GeneralUtil.ramdom9()+"";
        Order order = new Order();
        order.setOrderNo(orderNo);
        order.setPrice(orderDTO.getPrice());
        orderDao.save(orderDTO.getPrice(),orderNo);

    }


}
