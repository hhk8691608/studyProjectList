package com.mooc.jiangzh.dubbo.springboot;

import java.util.List;

public interface ServiceAPI {

    String sendMessage(String message);

    /**
     * 测试分组聚合
     * @return
     */
    public List<String> groupMergeList();

}
