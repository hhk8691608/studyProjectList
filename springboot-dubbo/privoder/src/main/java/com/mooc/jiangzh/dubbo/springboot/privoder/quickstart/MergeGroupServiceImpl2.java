package com.mooc.jiangzh.dubbo.springboot.privoder.quickstart;

import com.alibaba.dubbo.config.annotation.Service;
import com.mooc.jiangzh.dubbo.springboot.ServiceAPI;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Service(interfaceClass = ServiceAPI.class,group = "group_merge_1")
public class MergeGroupServiceImpl2 implements ServiceAPI {

    @Override
    public String sendMessage(String message) {
        return null;
    }

    @Override
    public List<String> groupMergeList() {
        List<String> resultList = new ArrayList<>();
        resultList.add("merge-2-1");
        resultList.add("merge-2-2");
        return resultList;
    }

}
