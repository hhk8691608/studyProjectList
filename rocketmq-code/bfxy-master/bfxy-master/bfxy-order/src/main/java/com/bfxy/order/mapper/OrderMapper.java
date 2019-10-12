package com.bfxy.order.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.bfxy.order.entity.Order;

public interface OrderMapper {
    int deleteByPrimaryKey(String orderId);

    int insert(Order record);

    int insertSelective(Order record);

    Order selectByPrimaryKey(String orderId);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);

	int updateOrderStatus(@Param("orderId")String orderId, @Param("orderStatus")String orderStatus, @Param("updateBy")String updateBy, @Param("updateTime")Date updateTime);
}