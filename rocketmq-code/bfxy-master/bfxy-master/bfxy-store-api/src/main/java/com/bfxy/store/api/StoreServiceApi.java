package com.bfxy.store.api;

import java.util.Date;

public interface StoreServiceApi {

    int selectVersion(String supplierId, String goodsId);
    int updateStoreCountByVersion(int currentVersion, String supplierId, String goodsId, String useName, Date currentTime);
    int selectStoreCount(String supplierId, String goodsId);


}
