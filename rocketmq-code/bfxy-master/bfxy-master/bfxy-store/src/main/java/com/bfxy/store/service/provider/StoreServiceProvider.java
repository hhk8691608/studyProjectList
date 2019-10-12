package com.bfxy.store.service.provider;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.dubbo.config.annotation.Service;
import com.bfxy.store.api.StoreServiceApi;
import com.bfxy.store.mapper.StoreMapper;
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class StoreServiceProvider implements StoreServiceApi {

	@Autowired
	private StoreMapper storeMapper;
	
	@Override
	public int selectVersion(String supplierId, String goodsId) {
		return storeMapper.selectVersion(supplierId, goodsId);
	}

	@Override
	public int updateStoreCountByVersion(int version, String supplierId, String goodsId, String updateBy,
			Date updateTime) {
		return storeMapper.updateStoreCountByVersion(version, supplierId, goodsId, updateBy, updateTime);
	}

	@Override
	public int selectStoreCount(String supplierId, String goodsId) {
		return storeMapper.selectStoreCount(supplierId, goodsId);
	}

}
