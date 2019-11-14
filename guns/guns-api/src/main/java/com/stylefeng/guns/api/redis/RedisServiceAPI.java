package com.stylefeng.guns.api.redis;

public interface RedisServiceAPI {


   String getSmsToken(String mobile);

   int checkSmsTokenLife(String mobile,String value);

}
