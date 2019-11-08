package com.mark.dockerproject.dao;

import com.mark.dockerproject.model.Item;
import com.mark.dockerproject.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ItemDao {

    @Insert("INSERT INTO item(price, name) VALUES(#{price}, #{name})")
    void insertUser(@Param("price") String price, @Param("orderNo") String orderNo);

    @Select("SELECT * FROM item WHERE id = #{id}")
    Item findItemById(@Param("id") int id);

}
