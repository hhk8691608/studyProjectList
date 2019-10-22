package com.mooc.jiangzh.dubbo.springboot.privoder.quickstart;

import com.alibaba.dubbo.config.annotation.Service;
import com.mooc.jiangzh.dubbo.springboot.UserInterface;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = UserInterface.class)
public class UserService implements UserInterface {


    @Override
    public String getUserById(Integer id) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("这是提供者= "+id);
        String str = stringBuffer.toString();
        System.out.println("服务提供方内容 ： "+str);
        return str;
    }


}
