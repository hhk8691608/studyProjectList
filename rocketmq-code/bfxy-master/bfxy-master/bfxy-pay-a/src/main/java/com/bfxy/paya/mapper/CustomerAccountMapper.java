package com.bfxy.paya.mapper;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.bfxy.paya.entity.CustomerAccount;

public interface CustomerAccountMapper {
    int deleteByPrimaryKey(String accountId);

    int insert(CustomerAccount record);

    int insertSelective(CustomerAccount record);

    CustomerAccount selectByPrimaryKey(String accountId);

    int updateByPrimaryKeySelective(CustomerAccount record);

    int updateByPrimaryKey(CustomerAccount record);

	int updateBalance(@Param("accountId")String accountId, @Param("newBalance")BigDecimal newBalance, @Param("version")int currentVersion, @Param("updateTime")Date currentTime);

}