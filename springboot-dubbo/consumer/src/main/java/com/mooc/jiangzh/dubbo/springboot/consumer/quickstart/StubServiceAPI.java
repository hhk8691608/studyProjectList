package com.mooc.jiangzh.dubbo.springboot.consumer.quickstart;

import com.mooc.jiangzh.dubbo.springboot.ServiceAPI;
import com.mooc.jiangzh.dubbo.springboot.UserInterface;

import java.util.List;

//服务消费者的Stub类

public class StubServiceAPI implements UserInterface {

    private UserInterface userLocalService ;

    public StubServiceAPI(UserInterface userLocalService ){
        this.userLocalService  = userLocalService ;
    }


    @Override
    public String getUserById(Integer id) {
        String result = null;
        try {

            if(id == 1){
                result = this.userLocalService.getUserById(id);
            }else {
                result = this.userLocalService.getUserById(id);
                result += "我瞎来的数据";
            }
            return result;

        }catch (Exception e){
            result = "异常数据";
            return result;
        }


    }
}
