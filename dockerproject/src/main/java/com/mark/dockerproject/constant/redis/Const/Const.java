package com.mark.dockerproject.constant.redis.Const;

public class Const {

    //统计登陆量
    public final static String STATISTICAL_LANGDING_AMOUNT = "StatisticalAmount";
    //初始化登陆次数
    public final static double STATISTICAL_LANGDING_INIT_NUM = 1D;
    //登陆步长
    public final static Integer STATISTICAL_LANGDING_LEN = 1;

    //登陆步长
    public final static double STATISTICAL_LANGDING_LEN_D = 1.0;

    //登录Token维系表
    public final static String LOGIN_USER_TOKENS = "LoginUserTokens";
    //token最后一次出现的时间
    public final static String LOGIN_RECENT_USER = "recent";
    //用户浏览过的商品
    public final static String USER_VIEWED="userViewed:";
    //用户购物车
    public final static String USER_CART="cart:";


}
