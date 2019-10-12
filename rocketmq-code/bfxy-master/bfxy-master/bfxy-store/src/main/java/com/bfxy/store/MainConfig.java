package com.bfxy.store;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bfxy.store.mapper")
@ComponentScan(basePackages = {"com.bfxy.store.*", "com.bfxy.store.config.*"})
public class MainConfig {

}
