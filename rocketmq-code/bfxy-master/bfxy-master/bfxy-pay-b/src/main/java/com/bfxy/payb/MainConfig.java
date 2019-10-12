package com.bfxy.payb;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bfxy.payb.mapper")
@ComponentScan(basePackages = {"com.bfxy.payb.*", "com.bfxy.payb.config.*"})
public class MainConfig {

}
