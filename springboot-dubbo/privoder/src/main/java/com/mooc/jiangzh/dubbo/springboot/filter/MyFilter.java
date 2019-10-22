package com.mooc.jiangzh.dubbo.springboot.filter;


import com.alibaba.dubbo.rpc.*;

public class MyFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("服务调用之前");
        return invoker.invoke(invocation);
    }
}
