package com.mooc.jiangzh.dubbo.springboot.consumer.quickstart;

import com.mooc.jiangzh.dubbo.springboot.DubboMockTestApi;
import org.springframework.stereotype.Component;
import com.alibaba.dubbo.config.annotation.Service;

//@Component
//@Service(interfaceClass = DubboMockTestApi.class,mock="com.mooc.jiangzh.dubbo.springboot.privoder.quickstart.DubboMockTestApiImpl")
public class DubboMockTestApiImpl implements DubboMockTestApi {


    @Override
    public String sayHello() {

        return "hello consumer mock!";
    }

    @Override
    public String sayWorld() {

        return "world consumer mock!";
    }
}
