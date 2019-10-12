package com.bfxy.store.api;
import java.util.Date;

public interface StoreServiceApi {

    public int selectVersion(String supplierId, String goodsId);

    public int updateStoreCountByVersion(int currentVersion ,String supplierId, String goodsId,String name,Date currentTime);

    public int selectStoreCount(String supplierId, String goodsId);

}
