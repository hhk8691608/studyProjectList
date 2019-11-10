package com.mark.dockerproject.dao;

import com.mark.dockerproject.model.Order;
import com.mark.dockerproject.model.User;
import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {



    @Insert("INSERT INTO `order` (price,order_no) VALUES( #{price}, #{orderNo} )")
    void save(@Param("price") String price,@Param("orderNo")String orderNo);

    @Select("SELECT id id,price price , order_no orderNo,supplier_id supplierId FROM `order`  WHERE id = #{id}")
    Order findOrderById(@Param("id") int id);



}
