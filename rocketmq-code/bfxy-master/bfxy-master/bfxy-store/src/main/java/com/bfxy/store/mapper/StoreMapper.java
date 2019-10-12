package com.bfxy.store.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.bfxy.store.entity.Store;

public interface StoreMapper {
    int deleteByPrimaryKey(String storeId);

    int insert(Store record);

    int insertSelective(Store record);

    Store selectByPrimaryKey(String storeId);

    int updateByPrimaryKeySelective(Store record);

    int updateByPrimaryKey(Store record);

	int selectVersion(@Param("supplierId")String supplierId, @Param("goodsId")String goodsId);

	int updateStoreCountByVersion(@Param("version")int version,@Param("supplierId") String supplierId,@Param("goodsId") String goodsId, @Param("updateBy") String updateBy,@Param("updateTime") Date updateTime);

	int selectStoreCount(@Param("supplierId")String supplierId, @Param("goodsId")String goodsId);

}