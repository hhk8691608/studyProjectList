package com.bfxy.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bfxy.order.mapper")
@ComponentScan(basePackages = {"com.bfxy.order.*", "com.bfxy.order.config.*"})
public class MainConfig {

}
