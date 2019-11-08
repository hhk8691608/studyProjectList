package com.mark.dockerproject.dao;

import org.apache.ibatis.annotations.*;

@Mapper
public interface OrderDao {



    @Insert("INSERT INTO `order` (price,order_no) VALUES( #{price}, #{orderNo} )")
    void save(@Param("price") String price,@Param("orderNo")String orderNo);


}
