package com.mooc.jiangzh.dubbo.springboot.consumer;

import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.QuickstartConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.RpcContextConsumer;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplicationRpcContext {

    public static void main(String[] args) {

        ConfigurableApplicationContext run =
                SpringApplication.run(ConsumerApplicationRpcContext.class, args);

        RpcContextConsumer rpcContextConsumer = (RpcContextConsumer)run.getBean("rpcContextConsumer");
        rpcContextConsumer.sendMessage();



    }
}
