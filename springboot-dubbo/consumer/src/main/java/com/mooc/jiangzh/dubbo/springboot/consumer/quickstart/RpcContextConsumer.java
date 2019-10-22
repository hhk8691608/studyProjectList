package com.mooc.jiangzh.dubbo.springboot.consumer.quickstart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.rpc.RpcContext;
import com.mooc.jiangzh.dubbo.springboot.ServiceAPI;
import org.springframework.stereotype.Component;
import com.mooc.jiangzh.dubbo.springboot.RpcContextParamter;

@Component
public class RpcContextConsumer {

    @Reference(interfaceClass = RpcContextParamter.class)
    RpcContextParamter serviceAPI;



    public void sendMessage(){
        for(int i=0;i<5;i++){
            RpcContext.getContext().setAttachment("parm", "嘿嘿"+i);
            serviceAPI.print();
        }
    }

}
