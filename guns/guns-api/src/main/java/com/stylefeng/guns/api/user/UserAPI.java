package com.stylefeng.guns.api.user;

import com.stylefeng.guns.api.redis.vo.UserLeaderboardDO;
import com.stylefeng.guns.api.user.vo.UserInfoModel;
import com.stylefeng.guns.api.user.vo.UserLeaderboardModel;
import com.stylefeng.guns.api.user.vo.UserModel;

import java.util.List;

public interface UserAPI {

    int login(String username, String password);

    boolean register(UserModel userModel);

    boolean checkUsername(String username);

    UserInfoModel getUserInfo(int uuid);

    UserInfoModel updateUserInfo(UserInfoModel userInfoModel);

    List<UserLeaderboardModel> getUserInfoTop(List<UserLeaderboardDO> reqBody);//获取浏览排行榜

}
