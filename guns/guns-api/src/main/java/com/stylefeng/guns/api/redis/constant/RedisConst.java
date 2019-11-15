package com.stylefeng.guns.api.redis.constant;


import java.io.Serializable;

public class RedisConst implements Serializable {


    //短信验证码有效时间
    public final static long SMS_LIFE_TIME = 600;
    //短信验证码
    public final static String SMS_TOKEN = "SmsToken:";

    //短信验证码存活
    public final static int SMS_TOKEM_SURVICE= 1;
    //短信验证码过期
    public final static int SMS_TOKEM_EXPIRED = 2;
    //短信验证码错误
    public final static int SMS_TOKEM_ERROR = 3;
    //短信验证码存活
    public final static int SMS_TOKEM_CORRECT= 4;


    //用户访问排行榜
    public final static String USER_ACCESS_WEB_TOP = "userAccessWebTop";
    //用户访问排行榜Score的步长
    public final static double USER_ACCESS_WEB_TOP_LEN = 1.0;

}
