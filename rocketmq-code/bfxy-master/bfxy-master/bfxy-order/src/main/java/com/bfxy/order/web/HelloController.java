package com.bfxy.order.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.bfxy.store.api.HelloServiceApi;

@RestController
public class HelloController {

    @Reference(version = "1.0.0",
            application = "${dubbo.application.id}",
            interfaceName = "com.bfxy.store.service.HelloServiceApi",
            check = false,
            timeout = 1000,
            retries = 0
    )
	private HelloServiceApi helloService;
    
    @RequestMapping("/hello")
	public String hello(@RequestParam String name) {
		return helloService.sayHello(name);
	}
}
