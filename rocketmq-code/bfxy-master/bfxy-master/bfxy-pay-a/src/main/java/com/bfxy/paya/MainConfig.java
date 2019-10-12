package com.bfxy.paya;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bfxy.paya.mapper")
@ComponentScan(basePackages = {"com.bfxy.paya.*", "com.bfxy.paya.config.*"})
public class MainConfig {

}
