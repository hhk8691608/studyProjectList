package com.mooc.jiangzh.dubbo.springboot.privoder.quickstart;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.dubbo.rpc.RpcContext;
import com.mooc.jiangzh.dubbo.springboot.DubboMockTestApi;
import com.mooc.jiangzh.dubbo.springboot.RpcContextParamter;
import org.springframework.stereotype.Component;

import java.util.Map;


@Component
@Service(interfaceClass = RpcContextParamter.class)
public class RpcContextParamterImpl implements RpcContextParamter {

    @Override
    public String print() {
        Map<String,String> attachments  = RpcContext.getContext().getAttachments();
        String parm = attachments.get("parm");
        System.out.println("producet parm = "+parm);
        return "hello";
    }
}
