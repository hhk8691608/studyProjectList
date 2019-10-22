package com.mooc.jiangzh.dubbo.springboot.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.DubboMockTestApiImpl;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.MockConsumer;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.QuickstartConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplicationMock {
//本地伪装
    public static void main(String[] args) {

        ConfigurableApplicationContext run =
                SpringApplication.run(ConsumerApplicationMock.class, args);

        MockConsumer mockConsumer = (MockConsumer)run.getBean("mockConsumer");

//        dubboMockTestApiImpl.sayHello();
        mockConsumer.sendMockSimple();
        mockConsumer.sendMockDdile();

    }
}
