package com.mooc.jiangzh.dubbo.springboot;

import java.util.List;

public interface ServiceAPI {

    String sendMessage(String message);
    public List<String> groupMergeList();
    public String sayHello(String name);

}
