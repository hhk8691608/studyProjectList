package com.mooc.jiangzh.dubbo.springboot.consumer.quickstart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mooc.jiangzh.dubbo.springboot.ServiceAPI;
import com.mooc.jiangzh.dubbo.springboot.UserInterface;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StubConsumer {
//本地存根
    @Reference(interfaceClass = UserInterface.class,stub = "com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.StubServiceAPI")
    StubServiceAPI stubServiceAPI;

    public String sendMessage(Integer id){
        return stubServiceAPI.getUserById(id);
    }


}
