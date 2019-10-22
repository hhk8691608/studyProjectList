package com.mooc.jiangzh.dubbo.springboot.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.GroupMergeConsumer;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.StubConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplicationStub {

    //本地存根Demo
    public static void main(String[] args) {

        ConfigurableApplicationContext run =
                SpringApplication.run(ConsumerApplicationStub.class, args);

        StubConsumer stubConsumer = (StubConsumer)run.getBean("stubConsumer");

        String result = stubConsumer.sendMessage(11);
        System.out.println("ConsumerApplicationStub result = "+result);

    }
}
