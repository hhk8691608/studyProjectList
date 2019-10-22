package com.mooc.jiangzh.dubbo.springboot.consumer.quickstart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mooc.jiangzh.dubbo.springboot.DubboMockTestApi;
import com.mooc.jiangzh.dubbo.springboot.UserInterface;
import org.springframework.stereotype.Component;

//本地伪装
@Component
public class MockConsumer {


    //本地伪装 简单的忽略异常
    @Reference(interfaceClass = DubboMockTestApi.class)
    DubboMockTestApi dubboMockTestApiSimple;


    //本地伪装  开启服务降级，返回指定mock数据
    @Reference(interfaceClass = DubboMockTestApi.class,mock = "com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.DubboMockTestApiImpl")
    DubboMockTestApi dubboMockTestApiImpl;


    public void sendMockSimple(){
        System.out.println(dubboMockTestApiSimple.sayHello());
        System.out.println("-------------sendMockSimple---------------");
    }

    public void sendMockDdile(){
        System.out.println(dubboMockTestApiImpl.sayHello());
    }


}
