package com.bfxy.store.service.provider;

import com.alibaba.dubbo.config.annotation.Service;
import com.bfxy.store.api.HelloServiceApi;
@Service(
        version = "1.0.0",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
public class HelloServiceProvider implements HelloServiceApi {

	@Override
	public String sayHello(String name) {
		System.err.println("name: " + name);
		return "hello " + name ;
	}

}
