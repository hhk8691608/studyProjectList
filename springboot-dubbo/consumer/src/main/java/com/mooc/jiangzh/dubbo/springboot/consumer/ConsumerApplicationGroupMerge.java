package com.mooc.jiangzh.dubbo.springboot.consumer;

import com.alibaba.dubbo.spring.boot.annotation.EnableDubboConfiguration;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.GroupMergeConsumer;
import com.mooc.jiangzh.dubbo.springboot.consumer.quickstart.QuickstartConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
@EnableDubboConfiguration
public class ConsumerApplicationGroupMerge {

    public static void main(String[] args) {

        ConfigurableApplicationContext run =
                SpringApplication.run(ConsumerApplicationGroupMerge.class, args);

        GroupMergeConsumer groupMergeConsumer = (GroupMergeConsumer)run.getBean("groupMergeConsumer");

        groupMergeConsumer.sendMessage("hello world");
        groupMergeConsumer.groupMergeShow();

    }
}
