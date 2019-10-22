package com.mooc.jiangzh.dubbo.springboot.privoder.quickstart;

import com.alibaba.dubbo.config.annotation.Service;
import com.mooc.jiangzh.dubbo.springboot.DubboMockTestApi;
import com.mooc.jiangzh.dubbo.springboot.ServiceAPI;
import org.springframework.stereotype.Component;


@Component
@Service(interfaceClass = DubboMockTestApi.class)
public class DubboMockTestApiImpl implements DubboMockTestApi {


    @Override
    public String sayHello() {
        System.out.println("这是服务提供者 sayHello");
        return null;
    }

    @Override
    public String sayWorld() {
        String str = "这是服务提供者 mock Service sayWorld";
        System.out.println(str);
        return str;
    }
}
