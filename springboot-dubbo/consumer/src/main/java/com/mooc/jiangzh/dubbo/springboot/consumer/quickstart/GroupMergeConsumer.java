package com.mooc.jiangzh.dubbo.springboot.consumer.quickstart;

import com.alibaba.dubbo.config.annotation.Reference;
import com.mooc.jiangzh.dubbo.springboot.ServiceAPI;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupMergeConsumer {
//
    @Reference(interfaceClass = ServiceAPI.class,group = "group_merge_1,group_merge_2")
    ServiceAPI serviceAPI;



    public void sendMessage(String message){
        System.out.println(serviceAPI.sendMessage(message));
    }

    public void groupMergeShow(){
        List<String> stringList = serviceAPI.groupMergeList();
        for(String str : stringList){
            System.out.println(str);
        }

    }

}
