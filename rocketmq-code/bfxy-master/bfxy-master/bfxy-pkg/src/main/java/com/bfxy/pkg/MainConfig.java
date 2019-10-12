package com.bfxy.pkg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan(basePackages = "com.bfxy.pkg.mapper")
@ComponentScan(basePackages = {"com.bfxy.pkg.*", "com.bfxy.pkg.config.*"})
public class MainConfig {

}
