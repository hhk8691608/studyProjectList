package com.bfxy.store.api.impl;

import com.bfxy.store.api.StoreServiceApi;

import java.util.Date;

public class StoreServiceApiImpl implements StoreServiceApi {


    @Override
    public int selectVersion(String supplierId, String goodsId) {
        return 0;
    }

    @Override
    public int updateStoreCountByVersion(int currentVersion, String supplierId, String goodsId, String name, Date currentTime) {
        return 0;
    }

    @Override
    public int selectStoreCount(String supplierId, String goodsId) {
        return 0;
    }
}
